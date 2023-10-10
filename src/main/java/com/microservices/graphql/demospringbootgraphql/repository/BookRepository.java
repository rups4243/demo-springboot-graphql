package com.microservices.graphql.demospringbootgraphql.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.microservices.graphql.demospringbootgraphql.model.Book;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BookRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookRepository.class);

	@Autowired
	private DatabaseClient databaseClient;

	public Mono<Book> getBook(int id) {
		//Mono<Map<String, Object>> result = 
				return databaseClient.sql("Select id, name, pages from books where id= :id ").
				bind("id", id).fetch().one().map(row -> {
					Book bk = new Book();
					bk.setId((int) row.get("id"));
					bk.setName((String) row.get("name"));
					bk.setPages((int) row.get("pages"));
					return bk;
				});
//		LOGGER.info(String.format("Data fetched --> %s", result));
//		Map<String, Object> map = result.block();
//		List<Object> listOfObject = map.entrySet().stream().map(a -> a.getValue()).collect(Collectors.toList());
//		Book bk = (Book) listOfObject.get(0);
//		return Mono.just(bk);
		//return listOfObject.get(0);
		//return null;
	}
	
	
	public Flux<Book> getAllBooks() {
		return 
		databaseClient.sql("Select id, name, pages from books").fetch().all().map(row -> {
			Book bk = new Book();
			bk.setId((int) row.get("id"));
			bk.setName((String) row.get("name"));
			bk.setPages((int) row.get("pages"));
			return bk;
		});
		
		
		
	}
}
