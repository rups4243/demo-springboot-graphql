package com.microservices.graphql.demospringbootgraphql;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import com.microservices.graphql.demospringbootgraphql.service.AuthorService;
import com.microservices.graphql.demospringbootgraphql.service.BookService;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;
import graphql.schema.idl.errors.SchemaProblem;
import io.r2dbc.spi.ConnectionFactory;

@SpringBootApplication
public class DemoSpringbootGraphqlApplication {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private AuthorService authorService;

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringbootGraphqlApplication.class, args);

	}

	@Bean
	public ConnectionFactoryInitializer connectionFactoryInitializer(ConnectionFactory factory) {
		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(factory);
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("schema.sql"), new ClassPathResource("data.sql"));
		initializer.setDatabasePopulator(populator);
		return initializer;
	}

	@Bean
	public GraphQL graphQL() throws SchemaProblem, IOException {
		SchemaParser schemaParser = new SchemaParser();
		ClassPathResource schema = new ClassPathResource("schema.graphql");
		TypeDefinitionRegistry definitionRegistry = schemaParser.parse(schema.getInputStream());
		// bookService.
		// new BookService().getBook();
		RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
				.type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getBook", bookService.getBook()))
				.type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getBooks", bookService.getBooks()))
				.type(TypeRuntimeWiring.newTypeWiring("Mutation").dataFetcher("createBook", bookService.createBook()))
				.type(TypeRuntimeWiring.newTypeWiring("Book").dataFetcher("author", authorService.getAuthor()))
				.build();

		SchemaGenerator generator = new SchemaGenerator();
		GraphQLSchema graphQLSchema = generator.makeExecutableSchema(definitionRegistry, runtimeWiring);

		return GraphQL.newGraphQL(graphQLSchema).build();

	}
}
