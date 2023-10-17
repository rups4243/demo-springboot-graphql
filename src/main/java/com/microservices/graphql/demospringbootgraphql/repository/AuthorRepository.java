package com.microservices.graphql.demospringbootgraphql.repository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.microservices.graphql.demospringbootgraphql.model.Author;
import com.microservices.graphql.demospringbootgraphql.model.Book;

import reactor.core.publisher.Mono;

@Repository
public class AuthorRepository {

	@Autowired
	private DatabaseClient databaseClient;
	
	public Mono<UUID> createAuthor(Author author) {
		UUID id = UUID.randomUUID();
		author.setId(id);
		Mono<Long> spec = databaseClient
				.sql("insert into authors (id, name, age, book_id) values (:id, :name, :age, :bookId)").bind("id", id)
				.bind("name", author.getName()).bind("age", author.getAge()).bind("bookId", author.getBookId()).fetch()
				.rowsUpdated();
		spec.subscribe(value -> System.out.println(value));
//				LOGGER.info(spec.fetch().all().toString());;
//				spec.fetch().all().
		return Mono.just(id);
	}
	
	
	
	public Mono<Author> getAuthor(UUID bookId) {
		// Mono<Map<String, Object>> result =
		return databaseClient.sql("Select id, name, age, book_id from authors where book_id= :id ").bind("id", bookId)
				.fetch().one().map(row -> {
					
					Author author = new Author(UUID.fromString((String) row.get("id")), (String) row.get("name"), (int) row.get("age"),
							bookId);
					return author;
				});
	}
}
