package com.microservices.graphql.demospringbootgraphql.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("books")
public class Book {

	@Id
	private UUID id;
	private String name;
	private int pages;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Book(String name, int pages) {
		super();
		this.name = name;
		this.pages = pages;
	}
	
	
	
	
}
