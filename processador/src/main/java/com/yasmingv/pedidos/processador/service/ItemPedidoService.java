package com.yasmingv.pedidos.processador.service;

import com.yasmingv.pedidos.processador.entity.ItemPedido;
import com.yasmingv.pedidos.processador.entity.Pedido;
import com.yasmingv.pedidos.processador.repository.ItemPedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;


    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public List<ItemPedido> salvar(List<ItemPedido> itens) {
        return itemPedidoRepository.saveAll(itens);
    }

    public void salvar(ItemPedido iten) {
        itemPedidoRepository.save(iten);
    }

    public void updateItemPedido(List<ItemPedido> itemPedidos, Pedido pedido) {
        itemPedidos.forEach(itemPedido -> {
            itemPedido.setPedido(pedido);
            this.salvar(itemPedido);
        });

    }
}
