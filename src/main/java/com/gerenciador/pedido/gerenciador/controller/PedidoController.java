package com.gerenciador.pedido.gerenciador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gerenciador.pedido.gerenciador.model.Pedido;
import com.gerenciador.pedido.gerenciador.model.Produto;
import com.gerenciador.pedido.gerenciador.repository.PedidoRepository;
import com.gerenciador.pedido.gerenciador.repository.ProdutoRepository;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/novo")
    public String showCreateForm(Model model) {
        model.addAttribute("produtos", produtoRepository.findAll());
        model.addAttribute("pedido", new Pedido());
        return "pedidos/create";
    }

    @PostMapping("/novo")
    public String createPedido(@ModelAttribute Pedido pedido) {
        Produto produtoSelecionado = produtoRepository.findById(pedido.getProduto().getId())
            .orElseThrow(() -> new IllegalArgumentException("Produto inválido: " + pedido.getProduto().getId()));
        pedido.setValorTotal(produtoSelecionado.getPreco() * pedido.getQuantidade());
        pedidoRepository.save(pedido);
        return "redirect:/pedidos";
    }

    @GetMapping
    public String listPedidos(Model model) {
        model.addAttribute("pedidos", pedidoRepository.findAll());
        return "pedidos/list";
    }
}