package com.yasmingv.pedidos.processador.listener;

import com.yasmingv.pedidos.processador.entity.Pedido;
import com.yasmingv.pedidos.processador.entity.enums.Status;
import com.yasmingv.pedidos.processador.service.PedidoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class PedidoListener {

    private final PedidoService pedidoService;

    public PedidoListener(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @RabbitListener(queues = "pedidos.v1.pedido-criado.gerar-processamento")
    private void salvarPedido(Pedido pedido) {
        pedido.setStatus(Status.PROCESSADO);
        pedidoService.salvarPedido(pedido);
    }
}
