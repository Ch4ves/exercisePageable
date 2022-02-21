package com.example.demo;

import java.util.List;
import java.util.Objects;

public class Company {
	private String nome;
	private String cnpj;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

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

	public Company(String nome, List<Produto> produtos) {
		super();
		this.nome = nome;
		this.produtos = produtos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj, nome, produtos);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}

		Company companyExternal = (Company) obj;
		if (this.getCnpj().equalsIgnoreCase(companyExternal.getCnpj())) {
			return true;
		}

		return false;
	}

	public Company() {
		super();
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

}
