package com.product.config;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apollographql.federation.graphqljava.Federation;
import com.product.fetcher.ProductDataFetcher;

import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;


@Configuration
public class ApolloConfig {
    @Bean
    public GraphQLSchema graphQLSchema(ProductDataFetcher productDataFetcher) {
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeRegistry = schemaParser.parse(new File("src/main/resources/graphql/schema.graphqls"));

        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
            .type("Query", builder -> builder
                .dataFetcher("products", productDataFetcher))
            .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema schema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);

        return Federation.transform(schema)
            .fetchEntities(env -> null)
            .resolveEntityType(env -> null)
            .build();
    }
}