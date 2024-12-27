package com.product.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import com.product.fetcher.GraphQLDataFetcher;
import com.product.fetcher.GraphQLResolver;
import graphql.scalars.ExtendedScalars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apollographql.federation.graphqljava.Federation;

import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;


@Configuration
public class ApolloConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .scalar(ExtendedScalars.DateTime)
                .scalar(ExtendedScalars.GraphQLBigDecimal)
                .scalar(ExtendedScalars.Json);
    }

    @Bean
    public GraphQLSchema graphQLSchema() {
        RuntimeWiring.Builder wiringBuilder = RuntimeWiring.newRuntimeWiring()
                .scalar(ExtendedScalars.DateTime)
                .scalar(ExtendedScalars.GraphQLBigDecimal)
                .scalar(ExtendedScalars.Json);

        // Automatically register all DataFetchers
        registerDataFetchers(wiringBuilder);

        RuntimeWiring runtimeWiring = wiringBuilder.build();

        // Load schema files
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeRegistry = loadSchemaFiles();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema schema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);

        return Federation.transform(schema)
                .fetchEntities(env -> null)
                .resolveEntityType(env -> null)
                .build();
    }

    private void registerDataFetchers(RuntimeWiring.Builder wiringBuilder) {
        // Get all classes annotated with @DataFetcherComponent
        Map<String, Object> dataFetcherBeans = applicationContext
                .getBeansWithAnnotation(GraphQLDataFetcher.class);

        dataFetcherBeans.values().forEach(bean -> {
            GraphQLDataFetcher annotation = bean.getClass()
                    .getAnnotation(GraphQLDataFetcher.class);
            String typeName = annotation.type();

            wiringBuilder.type(typeName, typeWiring -> {
                // Register all public methods as data fetchers
                Arrays.stream(bean.getClass().getMethods())
                        .filter(method -> method.isAnnotationPresent(GraphQLResolver.class))
                        .forEach(method -> {
                            GraphQLResolver resolverAnnotation =
                                    method.getAnnotation(GraphQLResolver.class);
                            String fieldName = resolverAnnotation.field();

                            typeWiring.dataFetcher(fieldName,
                                    env -> method.invoke(bean, env));
                        });
                return typeWiring;
            });
        });
    }

    private TypeDefinitionRegistry loadSchemaFiles() {
        TypeDefinitionRegistry registry = new TypeDefinitionRegistry();

        // Load schema files from resources/graphql directory
        Resource[] resources;
        try {
            resources = new PathMatchingResourcePatternResolver()
                    .getResources("classpath:graphql/**/*.graphqls");

            SchemaParser schemaParser = new SchemaParser();
            for (Resource resource : resources) {
                TypeDefinitionRegistry parsed = schemaParser
                        .parse(resource.getInputStream());
                registry.merge(parsed);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load schema files", e);
        }

        return registry;
    }
}