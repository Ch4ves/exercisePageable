package com.example.demo;

public class Produto {
	private String nome;
	private Company company;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Produto() {
		super();
	}

	public Produto(String nome, Company company) {
		super();
		this.nome = nome;
		this.company = company;
	}

	

}
