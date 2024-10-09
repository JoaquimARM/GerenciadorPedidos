package com.gerenciador.pedido.gerenciador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gerenciador.pedido.gerenciador.model.Produto;
import com.gerenciador.pedido.gerenciador.repository.ProdutoRepository;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/novo")
    public String showCreateForm(Model model) {
        model.addAttribute("produto", new Produto());
        return "produtos/criarProduto";
    }

    @PostMapping("/novo")
    public String createProduto(@ModelAttribute Produto produto) {
        produtoRepository.save(produto);
        return "redirect:/produtos";
    }

    @GetMapping
    public String listProdutos(Model model) {
        model.addAttribute("produtos", produtoRepository.findAll());
        return "produtos/listarProduto";
    }
    
    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Produto inválido: " + id));
        model.addAttribute("produto", produto);
        return "produtos/editarProduto";
    }

    // Atualiza o produto no banco
    @PostMapping("/editar/{id}")
    public String updateProduto(@PathVariable Long id, @ModelAttribute Produto produto) {
        produtoRepository.save(produto); // Atualiza o produto
        return "redirect:/produtos";
    }

    // Remove o produto
    @GetMapping("/remover/{id}")
    public String deleteProduto(@PathVariable Long id) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Produto inválido: " + id));
        produtoRepository.delete(produto);
        return "redirect:/produtos";
    }
    
}