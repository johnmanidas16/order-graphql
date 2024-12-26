package com.product.fetcher;

import org.springframework.stereotype.Component;

import com.product.record.product.Product;
import com.product.repository.ProductRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Flux;

@Component
public class ProductDataFetcher implements DataFetcher<Flux<Product>> {
    private final ProductRepository productRepository;

    public ProductDataFetcher(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Flux<Product> get(DataFetchingEnvironment environment) {
        return productRepository.findAll();
    }
}