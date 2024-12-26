package com.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import graphql.schema.idl.RuntimeWiring;

@Configuration
public class GraphQLConfig {
    @Bean
    public RuntimeWiring.Builder schemaGeneratorConfig() {
        return RuntimeWiring.newRuntimeWiring();
    }
}