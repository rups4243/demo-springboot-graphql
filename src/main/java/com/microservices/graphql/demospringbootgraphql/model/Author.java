package com.microservices.graphql.demospringbootgraphql.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("authors")
public class Author {
    @Id
	private UUID id;
    private String name;
    private int age;
    
    @Column("book_id")
    private UUID bookId;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public UUID getBookId() {
		return bookId;
	}

	public void setBookId(UUID bookId) {
		this.bookId = bookId;
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", name=" + name + ", age=" + age + ", bookId=" + bookId + "]";
	}

	public Author(UUID id, String name, int age, UUID bookId) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.bookId = bookId;
	}
    
    
}
