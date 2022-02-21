
package com.example.demo;

import java.util.List;

import org.springframework.web.servlet.resource.ResourceTransformerSupport;

public class Modelo  {

	private String teste;
	private List<List<Aprovado>> enums;

	public String getTeste() {
		return teste;
	}

	public void setTeste(String teste) {
		this.teste = teste;
	}

	public List<List<Aprovado>> getEnums() {
		return enums;
	}

	public void setEnums(List<List<Aprovado>> enums) {
		this.enums = enums;
	}

	public Modelo() {
		super();
	}

}
