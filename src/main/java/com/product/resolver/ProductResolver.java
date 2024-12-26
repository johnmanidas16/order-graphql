package com.product.resolver;

import org.springframework.stereotype.Component;

import com.product.record.product.Product;
import com.product.repository.ProductRepository;

import graphql.kickstart.tools.GraphQLResolver;
import reactor.core.publisher.Mono;

@Component
public class ProductResolver implements GraphQLResolver<Product> {
    private final ProductRepository productRepository;

    public ProductResolver(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Product> product(String id) {
        return productRepository.findById(id);
    }
}