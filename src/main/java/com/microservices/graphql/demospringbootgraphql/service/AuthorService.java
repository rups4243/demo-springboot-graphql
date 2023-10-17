package com.microservices.graphql.demospringbootgraphql.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.graphql.demospringbootgraphql.model.Author;
import com.microservices.graphql.demospringbootgraphql.model.Book;
import com.microservices.graphql.demospringbootgraphql.repository.AuthorRepository;

import graphql.schema.DataFetcher;
import reactor.core.publisher.Mono;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;
	
	public Mono<String> createAuthor(String authorName, int age, UUID bookId) {
		return authorRepository.createAuthor(new Author(null, authorName, age, bookId)).map(Object::toString);
	}
	
	public DataFetcher<CompletableFuture<Author>> getAuthor() {
		return env -> {
			Book book = env.getSource();
			return authorRepository.getAuthor(book.getId()).toFuture();
		};
	}
	
}
