package com.microservices.graphql.demospringbootgraphql.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.DatabaseClient.GenericExecuteSpec;
import org.springframework.stereotype.Repository;

import com.microservices.graphql.demospringbootgraphql.model.Book;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BookRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookRepository.class);

	@Autowired
	private DatabaseClient databaseClient;

	public Mono<Book> getBook(String id) {
		//Mono<Map<String, Object>> result = 
				return databaseClient.sql("Select id, name, pages from books where id= :id ").
				bind("id", id).fetch().one().map(row -> {
					Book bk = new Book();
					String bookid =  (String) row.get("id");
					System.out.println("book" + bookid);
					bk.setId(UUID.fromString(bookid));
					//bk.setId( (UUID) row.get("id"));
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
			Object id =  row.get("id");
			System.out.println("book" + id);
			bk.setId(UUID.fromString((String) id));
			bk.setName((String) row.get("name"));
			bk.setPages((int) row.get("pages"));
			System.out.println("book ids" + bk.getId());
			return bk;
		});
		
		
		
	}
	
	
	public Mono<UUID> createBook(Book book) {
		LOGGER.info(book.getName());
		//LOGGER.info(book.getId().toString());
		LOGGER.info(String.valueOf(book.getPages()));
		UUID bookId = UUID.randomUUID();
		book.setId(bookId);
		Mono<Long> spec =  databaseClient.sql("insert into books (id, name, pages) values (:id, :name, :pages)").
		bind("id", bookId).bind("name", book.getName()).bind("pages", book.getPages()).fetch().rowsUpdated();
		spec.subscribe(value -> System.out.println(value));
//		LOGGER.info(spec.fetch().all().toString());;
//		spec.fetch().all().
		return Mono.just(bookId);
		
	}
	
	
	
}
