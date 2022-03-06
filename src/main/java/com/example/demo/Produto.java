package com.example.demo;

public class Produto {
	private String nome;
	private String cnpj;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Produto() {
		super();
	}

	public Produto(String nome, String cnpj) {
		super();
		this.nome = nome;
		this.cnpj = cnpj;
	}

}
