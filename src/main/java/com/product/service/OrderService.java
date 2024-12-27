package com.product.service;

import com.product.input.*;
import com.product.record.order.*;
import com.product.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Mono<OrderDetails> createOrder(CreateOrderInput input) {
        OrderDetails order = OrderDetails.builder()
                .orderNumber(generateOrderNumber())
                .customerId(input.getCustomerId())
                .storeId(input.getStoreId())
                .status(OrderStatus.CREATED)
                .orderDate(LocalDateTime.now())
                .items(input.getItems().stream()
                        .map(this::mapToOrderItem)
                        .collect(Collectors.toList()))
                .shippingDetails(mapToShippingDetails(input.getShippingAddress(),
                        input.getShippingPriority()))
                .billingAddress(mapAddressInputToAddress(input.getBillingAddress()))
                .createdAt(LocalDateTime.now())
                .statusHistory(List.of(new OrderStatusHistory(
                        OrderStatus.CREATED,
                        "Order created",
                        LocalDateTime.now(),
                        "SYSTEM"
                )))
                .build();

        return orderRepository.save(order);
    }

    public Mono<OrderDetails> updateOrderStatus(UpdateOrderStatusInput input) {
        return orderRepository.findById(input.getOrderId())
                .flatMap(order -> {
                    List<OrderStatusHistory> updatedHistory = new ArrayList<>(order.getStatusHistory());
                    updatedHistory.add(new OrderStatusHistory(
                            input.getStatus(),
                            input.getComment(),
                            LocalDateTime.now(),
                            "SYSTEM"
                    ));

                    // Create new OrderDetails instance with updated values
                    OrderDetails updatedOrder = OrderDetails.builder()
                            .id(order.getId())
                            .orderNumber(order.getOrderNumber())
                            .customerId(order.getCustomerId())
                            .storeId(order.getStoreId())
                            .status(input.getStatus())  // Updated status
                            .orderDate(order.getOrderDate())
                            .lastModifiedDate(LocalDateTime.now()) // Update lastModifiedDate
                            .lastModifiedBy("SYSTEM")           // Update lastModifiedBy
                            .items(order.getItems())
                            .priceBreakdown(order.getPriceBreakdown())
                            .paymentDetails(order.getPaymentDetails())
                            .shippingDetails(order.getShippingDetails())
                            .billingAddress(order.getBillingAddress())
                            .customerNotes(order.getCustomerNotes())
                            .metadata(order.getMetadata())
                            .version(order.getVersion())
                            .createdAt(order.getCreatedAt())
                            .createdBy(order.getCreatedBy())
                            .statusHistory(updatedHistory)     // Updated history
                            .internalNotes(order.getInternalNotes())
                                    .build();

                    return orderRepository.save(updatedOrder);
                });
    }

    public Flux<OrderDetails> getOrdersByCustomer(String customerId,
                                                  Integer limit,
                                                  Integer offset) {
        Pageable pageable = PageRequest.of(offset != null ? offset : 0,
                limit != null ? limit : 10);
        return orderRepository.findByCustomerId(customerId, pageable);
    }

    public Flux<OrderDetails> getOrdersByStatus(OrderStatus status,
                                                Integer limit,
                                                Integer offset) {
        Pageable pageable = PageRequest.of(offset != null ? offset : 0,
                limit != null ? limit : 10);
        return orderRepository.findByStatus(status, pageable);
    }

    public Mono<OrderDetails> addOrderNote(AddOrderNoteInput input) {
        return orderRepository.findById(input.getOrderId())
                .flatMap(order -> {
                    OrderNote note = new OrderNote(
                            input.getNote(),
                            "SYSTEM",
                            LocalDateTime.now(),
                            input.isCustomerVisible()
                    );
                    order.getInternalNotes().add(note);
                    return orderRepository.save(order);
                });
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis();
    }

    private OrderItem mapToOrderItem(OrderItemInput input) {
        return OrderItem.builder()
                .productId(input.getProductId())
                .quantity(input.getQuantity())
                .customizations(input.getCustomizations())
                .build();
    }

    private Address mapAddressInputToAddress(AddressInput addressInput){
        Address address = new Address();
        BeanUtils.copyProperties(addressInput, address);
        return address;
    }
    private ShippingDetails mapToShippingDetails(AddressInput addressInput,
                                                 ShippingPriority priority) {
        Address address = new Address();
        BeanUtils.copyProperties(addressInput, address);
        return ShippingDetails.builder()
                .shippingAddress(address)
                .priority(priority)
                .estimatedDeliveryDate(calculateEstimatedDelivery(priority))
                .shipmentHistory(new ArrayList<>())
                .build();
    }

    private LocalDateTime calculateEstimatedDelivery(ShippingPriority priority) {
        return switch (priority) {
            case SAME_DAY -> LocalDateTime.now().plusHours(12);
            case NEXT_DAY -> LocalDateTime.now().plusDays(1);
            case EXPRESS -> LocalDateTime.now().plusDays(2);
            case STANDARD -> LocalDateTime.now().plusDays(5);
        };
    }

    public Mono<OrderDetails> getOrderById(String orderId) {
        return orderRepository.findById(orderId);
    }

    public Mono<OrderDetails> getOrderByNumber(String orderId) {
        return orderRepository.findByOrderNumber(orderId);
    }
}
