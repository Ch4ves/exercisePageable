package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class Poc {

	private ModelMapper modelMapper;

	private static String URL = "https://api.company.com.br/produtos";

	private static String PAGESIZE = "?pageSize=";

	private static String PAGE = "&page=";

	@GetMapping("/")
	private ResponseEntity<Object> teste(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int pageSize) throws IOException {

		ObjectMapper mapper = new ObjectMapper();

		Companies companies = mapper.readValue(new File("payload.json"), Companies.class);
		Companies paginatedList = (Companies) this.paginatedList(companies, new Paginacao(pageSize, page));

		Companies metaAndPage = (Companies) this.metaAndPage(companies, new Paginacao(pageSize, page));

		paginatedList.setLinks(metaAndPage.getLinks());
		paginatedList.setMeta(metaAndPage.getMeta());
		return ResponseEntity.ok().body(paginatedList);

	}

	private Object paginatedList(Companies companies, Paginacao paginacao) {

		Companies retorno = new Companies();

		List<Produto> produtos = new ArrayList<>();

		List<Company> companias = new ArrayList<>();

		companies.getCompanies().forEach(c -> c.getProdutos().forEach(p -> p.setCnpj(c.getCnpj())));
		companies.getCompanies().forEach(c -> produtos.addAll(c.getProdutos()));

		List<Produto> produtosPaginados = produtos.stream().skip((paginacao.getPage() - 1) * paginacao.getPageSize())
				.limit(paginacao.getPageSize()).collect(Collectors.toList());

		for (Company comp : companies.getCompanies()) {
			Company company = new Company();
			company.setCnpj(comp.getCnpj());
			company.setNome(comp.getNome());

			for (Produto produto : produtosPaginados) {
				if (comp.getCnpj().equals(produto.getCnpj())) {
					company.getProdutos().add(produto);
				}
			}
			if (!company.getProdutos().isEmpty()) {
				companias.add(company);
			}
		}

		retorno.setCompanies(companias);

		return retorno;

	}

	private Companies metaAndPage(Companies companies, Paginacao paginacao) {
		//Merda de implementacao mas .... n tinha tempo. Sorry, eu do futuro.
		Meta meta = new Meta();
		Links links = new Links();

		int totalRecords = 0;

		for (Company company : companies.getCompanies()) {
			totalRecords = totalRecords + company.getProdutos().size();
		}

		int lastPage = totalRecords / paginacao.getPageSize();

		links.setFirst(URL + PAGESIZE + paginacao.getPageSize() + PAGE + "1");

		if (lastPage == 0) {
			links.setLast(URL + PAGESIZE + paginacao.getPageSize() + PAGE + "1");
		} else {
			links.setLast(URL + PAGESIZE + paginacao.getPageSize() + PAGE + this.selfLastPage(totalRecords, paginacao));

		}

		if (paginacao.getPage() < lastPage && lastPage != 0) {

			links.setNext(URL + PAGESIZE + paginacao.getPageSize() + PAGE + this.selfNextPage(paginacao, lastPage));

		} else {
			links.setNext("");

		}

		if (paginacao.getPage() > 1 && lastPage != 0) {
			links.setPrev(URL + PAGESIZE + paginacao.getPageSize() + PAGE + (paginacao.getPage() - 1));

		} else {
			links.setPrev("");
		}

		links.setSelf(URL + PAGESIZE + paginacao.getPageSize() + PAGE + paginacao.getPage());

		if (paginacao.getPageSize() > totalRecords) {
			meta.setTotalPages(1);

		} else {

			if (totalRecords == ((totalRecords / paginacao.getPageSize() == 1 ? 2
					: totalRecords / paginacao.getPageSize() == 1 ? totalRecords / paginacao.getPageSize()
							: totalRecords / paginacao.getPageSize()))) {
				meta.setTotalPages(totalRecords);
			} else {

				meta.setTotalPages(((totalRecords / paginacao.getPageSize() == 1 ? 2
						: totalRecords / paginacao.getPageSize() == 1 ? totalRecords / paginacao.getPageSize()
								: totalRecords / paginacao.getPageSize() + 1)));

			}

		}

		meta.setTotalRecords(totalRecords);

		companies.setLinks(links);
		companies.setMeta(meta);

		return companies;

	}

	private int selfNextPage(Paginacao paginacao, int lastPage) {

		if (paginacao.getPage() < lastPage) {
			return paginacao.getPage() + 1;
		}

		return 0;

	}

	private int selfLastPage(int totalRecords, Paginacao paginacao) {

		if (totalRecords == ((totalRecords / paginacao.getPageSize() == 1 ? 2
				: totalRecords / paginacao.getPageSize() == 1 ? totalRecords / paginacao.getPageSize()
						: totalRecords / paginacao.getPageSize()))) {
			return totalRecords;
		}

		return ((totalRecords / paginacao.getPageSize() == 1 ? 2
				: totalRecords / paginacao.getPageSize() == 1 ? totalRecords / paginacao.getPageSize()
						: totalRecords / paginacao.getPageSize() + 1));

	}

	@PostConstruct
	private void configure() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		PropertyMap<Companies, CompaniesDTO> mymap = new PropertyMap<Companies, CompaniesDTO>() {
			protected void configure() {
			}
		};

	}

}
