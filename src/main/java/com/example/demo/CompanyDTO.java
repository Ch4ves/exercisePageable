package com.example.demo;

import java.util.List;

public class CompanyDTO {
	private String nome;
	public long cnpj;

	private List<Produto> produtos;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public CompanyDTO(String nome, List<Produto> produtos) {
		super();
		this.nome = nome;
		this.produtos = produtos;
	}

	public CompanyDTO() {
		super();
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

}
