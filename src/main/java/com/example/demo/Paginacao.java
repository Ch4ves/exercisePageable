package com.example.demo;

public class Paginacao {
	
	public int pageSize;
	public int page;
	public Paginacao(int pageSize, int page) {
		super();
		this.pageSize = pageSize;
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}
	public int totalElements;
	

}
