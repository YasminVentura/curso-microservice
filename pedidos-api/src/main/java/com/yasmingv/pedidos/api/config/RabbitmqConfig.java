package com.yasmingv.pedidos.api.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Value("${rabbit.exchange.name}")
    private String exchangeName;

    @Bean //retorna a exchange do tipo fanout
    public Exchange exchange() {
        return new FanoutExchange(exchangeName);
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
