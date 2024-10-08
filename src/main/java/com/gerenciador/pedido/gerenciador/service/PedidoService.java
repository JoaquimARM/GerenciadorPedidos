package com.gerenciador.pedido.gerenciador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gerenciador.pedido.gerenciador.model.Pedido;
import com.gerenciador.pedido.gerenciador.model.StatusEnum;
import com.gerenciador.pedido.gerenciador.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void mudarStatus(Long id, StatusEnum novoStatus) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow();
        pedido.mudarStatus(novoStatus);
        pedidoRepository.save(pedido);
    }
}