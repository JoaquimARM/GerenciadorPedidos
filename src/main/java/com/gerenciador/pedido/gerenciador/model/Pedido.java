package com.gerenciador.pedido.gerenciador.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private List<Produto> produtos = new ArrayList<>();  // Lista de produtos selecionados

    private int quantidade;
    private double valorTotal; // Valor total baseado na soma dos produtos

    private LocalDate data = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ABERTO;

    public void mudarStatus(StatusEnum novoStatus) {
        this.status = novoStatus;
    }
    
    public void calcularValorTotal() {
        this.valorTotal = this.produtos.stream()
            .mapToDouble(produto -> produto.getPreco() * this.quantidade)
            .sum();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	
    
    
}