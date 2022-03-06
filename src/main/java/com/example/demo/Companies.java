package com.example.demo;

import java.util.List;

public class Companies {

	public Companies() {
		super();
	}

	private List<Company> companies;
	private Meta meta;
	private Links links;

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public Links getLinks() {
		return links;
	}

	public void setLinks(Links links) {
		this.links = links;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}



}
