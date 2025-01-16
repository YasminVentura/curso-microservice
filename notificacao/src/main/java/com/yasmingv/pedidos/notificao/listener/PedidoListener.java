package com.yasmingv.pedidos.notificao.listener;

import com.yasmingv.pedidos.notificao.entity.Pedido;
import com.yasmingv.pedidos.notificao.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoListener {

    private final Logger log = LoggerFactory.getLogger(PedidoListener.class);

    private final EmailService emailService;

    public PedidoListener(EmailService emailService) {
        this.emailService = emailService;
    }


    @RabbitListener(queues = "pedidos.v1.pedido-criado.gerar-notificacao")
    public void gerarNotidicacao (Pedido pedido){
        log.info("Notificação gerada: {}", pedido);
        emailService.enviarEmail(pedido);
    }
}
