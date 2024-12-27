package com.product.repository;

import com.product.record.order.OrderDetails;
import com.product.record.order.OrderStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<OrderDetails, String> {
    Flux<OrderDetails> findByCustomerId(String customerId, Pageable pageable);
    Flux<OrderDetails> findByStatus(OrderStatus status, Pageable pageable);
    Mono<OrderDetails> findByOrderNumber(String orderNumber);
    Flux<OrderDetails> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
