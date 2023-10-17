package com.microservices.graphql.demospringbootgraphql.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.graphql.demospringbootgraphql.model.Author;
import com.microservices.graphql.demospringbootgraphql.model.Book;
import com.microservices.graphql.demospringbootgraphql.repository.AuthorRepository;
import com.microservices.graphql.demospringbootgraphql.repository.BookRepository;

import graphql.schema.DataFetcher;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AuthorRepository authorRepository;

	public DataFetcher<CompletableFuture<Book>> getBook() {
		return env -> {
			String bookId = env.getArgument("id");
			return bookRepository.getBook(bookId).toFuture();
		};
	}

	public DataFetcher<CompletableFuture<List<Book>>> getBooks() {
		return env -> {
			if (bookRepository.getAllBooks() != null)
				return bookRepository.getAllBooks().collectList().toFuture();
			else
				return null;
		};
	}
	
	public DataFetcher<CompletableFuture<String>> createBook(){
		return env -> {
			String bookName = env.getArgument("bookName");
			String authorName = env.getArgument("authorName");
			int pages = env.getArgument("pages");
			int age = env.getArgument("age");
			Book book = new Book(bookName, pages);
			return bookRepository.createBook(book).flatMap(bookId -> 
				authorRepository.createAuthor(new Author(null, authorName, age, bookId)).map(authorId -> bookId.toString())
			).toFuture();
			
			
					
		};
	}
	
}
