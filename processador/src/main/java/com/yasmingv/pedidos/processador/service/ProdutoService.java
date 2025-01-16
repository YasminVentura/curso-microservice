package com.yasmingv.pedidos.processador.service;

import com.yasmingv.pedidos.processador.entity.ItemPedido;
import com.yasmingv.pedidos.processador.entity.Produto;
import com.yasmingv.pedidos.processador.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void salvar(List<ItemPedido> itens) {
        itens.forEach(item -> {
            produtoRepository.save(item.getProduto());
        });
    }

}
