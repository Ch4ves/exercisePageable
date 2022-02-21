package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class Poc {

	private ModelMapper modelMapper;

	@GetMapping("/")
	private ResponseEntity<CompaniesDTO> teste() throws IOException {

		ObjectMapper mapper = new ObjectMapper();

		Companies companies = mapper.readValue(new File("payload.json"), Companies.class);

		return ResponseEntity.ok().body(this.paginatedList(companies, new Paginacao(13, 1)));

	}

	private CompaniesDTO paginatedList(Companies companies, Paginacao paginacao) {

		Companies retorno = new Companies();
		companies.getCompanies().forEach(c -> c.getProdutos().forEach(p -> p.setCompany(c)));
		Set<Company> set = new HashSet<>();


		List<Produto> produtos = new ArrayList<>();
		
		companies.getCompanies().forEach(c -> produtos.addAll(c.getProdutos()));
		
		List<Produto> produtosPaginados = produtos.stream().skip((paginacao.getPage() - 1) * paginacao.getPageSize())
				.limit(paginacao.getPageSize()).collect(Collectors.toList());
		produtosPaginados.forEach(p -> p.getCompany().getProdutos().removeAll(produtos));
		produtosPaginados.forEach(p -> set.add(p.getCompany()));
		
		retorno.setCompanies(new ArrayList<>(set));
		
		
		
		return modelMapper.map(retorno, CompaniesDTO.class);

	}

	private Companies addCompany(List<Produto> produtos, List<Company> companiesReference) {

		Companies retorno = new Companies();

		return retorno;
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
