package com.yasmingv.pedidos.notificao.service;

import com.yasmingv.pedidos.notificao.entity.Pedido;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmail(Pedido pedido) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("pedidos-api@company.com");
        msg.setTo(pedido.getEmail());
        msg.setSubject("Pedido de compra");
        msg.setText(this.gerarMensagem(pedido));
        mailSender.send(msg);
    }

    private String gerarMensagem(Pedido pedido) {
        String id = pedido.getId().toString();
        String cliente = pedido.getCliente();
        String valor = String.valueOf(pedido.getValorTotal());
        String status = pedido.getStatus().name();

        return String.format("Ola %s, seu pedido de n° %s no valor de %s, foi realizaado com sucesso.\n " +
                "Status: %s.", cliente, id, valor, status);
    }
}
