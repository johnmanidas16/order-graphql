package com.product.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.product.record.product.Product;
import com.product.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryMapping
    public Flux<Product> products() {
        return productRepository.findAll();
    }

    @QueryMapping
    public Mono<Product> productById(@Argument String id) {
        return productRepository.findById(id);
    }

    @MutationMapping
    public Mono<Product> addProduct(@Argument String name, 
                                  @Argument String description,
                                  @Argument BigDecimal price,
                                  @Argument Integer stockQuantity) {
        Product product = new Product(null, name, description, price, stockQuantity, LocalDateTime.now());
        return productRepository.save(product);
    }

    @MutationMapping
    public Mono<Product> updateProduct(@Argument String id,
                                     @Argument String name,
                                     @Argument String description,
                                     @Argument BigDecimal price,
                                     @Argument Integer stockQuantity) {
        return productRepository.findById(id)
            .map(p -> new Product(id, name, description, price, stockQuantity, LocalDateTime.now()))
            .flatMap(productRepository::save);
    }

    @MutationMapping
    public Mono<Boolean> deleteProduct(@Argument String id) {
        return productRepository.deleteById(id)
            .thenReturn(true);
    }
}