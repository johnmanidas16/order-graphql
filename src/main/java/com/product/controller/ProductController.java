package com.product.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.product.input.ProductInput;
import org.springframework.beans.BeanUtils;
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
    public Mono<Product> addProduct(@Argument ProductInput input) {
        Product product = new Product();
        BeanUtils.copyProperties(input, product);
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    @MutationMapping
    public Mono<Product> updateProduct(@Argument ProductInput productInput) {
        return productRepository.findById(productInput.getId())
            .flatMap(productRepository::save);
    }

    @MutationMapping
    public Mono<Boolean> deleteProduct(@Argument String id) {
        return productRepository.deleteById(id)
            .thenReturn(true);
    }
}