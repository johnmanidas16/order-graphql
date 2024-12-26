package com.product.config;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

@Configuration
public class CustomScalars {

	 @Bean
	    public GraphQLScalarType dateScalar() {
	        return GraphQLScalarType.newScalar()
	            .name("DateTime")
	            .description("DateTime scalar")
	            .coercing(new Coercing<LocalDateTime, String>() {
	                @Override
	                public String serialize(Object input) {
	                    if (input instanceof LocalDateTime) {
	                        return ((LocalDateTime) input).toString();
	                    }
	                    throw new CoercingSerializeException("Invalid DateTime");
	                }

	                @Override
	                public LocalDateTime parseValue(Object input) {
	                    return LocalDateTime.parse((String) input);
	                }

	                @Override
	                public LocalDateTime parseLiteral(Object input) {
	                    return LocalDateTime.parse(((StringValue) input).getValue());
	                }
	            })
	            .build();
	    }
}
