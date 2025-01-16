package com.yasmingv.pedidos.processador.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

@Configuration
public class RabbitmqConfig {

    @Value("${rabbit.exchange.name}")
    private String exchangeName;

    @Value("${rabbit.queue.name}")
    private String processadorQueue;

    @Bean // Retorna a exchange do tipo fanout
    public FanoutExchange pedidosExchange() {
        return new FanoutExchange(exchangeName);
    }

    @Bean
    public Queue notificacaoQueue() {
        return new Queue(processadorQueue);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(notificacaoQueue()).to(pedidosExchange());
    }

    @Bean //spring injeta a conexao com o rabbitmq no rabbitadmin
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean //converte o objeto em json
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean //envia a mensagem, template
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean //configura tudo para gente quando a aplicação subir
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEvent(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }
}
