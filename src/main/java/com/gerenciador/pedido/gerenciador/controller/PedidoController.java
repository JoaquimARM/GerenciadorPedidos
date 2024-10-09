package com.gerenciador.pedido.gerenciador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "pedidos/criarPedido";
    }

    @PostMapping("/novo")
    public String createPedido(@ModelAttribute Pedido pedido, @RequestParam List<Long> produtoIds) {
        // Carregar produtos selecionados e adicionar ao pedido
        List<Produto> produtosSelecionados = produtoRepository.findAllById(produtoIds);
        pedido.setProdutos(produtosSelecionados);

        // Calcular o valor total do pedido
        pedido.calcularValorTotal();

        // Salvar o pedido
        pedidoRepository.save(pedido);
        return "redirect:/pedidos";
    }

    @GetMapping
    public String listPedidos(Model model) {
        model.addAttribute("pedidos", pedidoRepository.findAll());
        return "pedidos/listaPedidos";
    }
    
 // Exibe o formulário de edição de pedido
    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Pedido inválido: " + id));
        model.addAttribute("pedido", pedido);
        model.addAttribute("produtos", produtoRepository.findAll()); // Carrega todos os produtos
        return "pedidos/editarPedido";
    }

    // Atualiza o pedido no banco
    @PostMapping("/editar/{id}")
    public String updatePedido(@PathVariable Long id, @ModelAttribute Pedido pedido, @RequestParam List<Long> produtoIds) {
        List<Produto> produtosSelecionados = produtoRepository.findAllById(produtoIds);
        pedido.setProdutos(produtosSelecionados);
        pedido.calcularValorTotal();
        pedidoRepository.save(pedido);
        return "redirect:/pedidos";
    }

    // Remove o pedido
    @GetMapping("/remover/{id}")
    public String deletePedido(@PathVariable Long id) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Pedido inválido: " + id));
        pedidoRepository.delete(pedido);
        return "redirect:/pedidos";
    }
    
    
}