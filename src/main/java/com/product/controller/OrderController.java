package com.product.controller;

import com.product.fetcher.GraphQLDataFetcher;
import com.product.fetcher.GraphQLResolver;
import com.product.input.AddOrderNoteInput;
import com.product.input.CreateOrderInput;
import com.product.input.UpdateOrderStatusInput;
import com.product.record.order.OrderDetails;
import com.product.record.order.OrderStatus;
import com.product.service.OrderService;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     *
     * @param id
     * @return
     */
    @QueryMapping
    public Mono<OrderDetails> orderById(@Argument String id) {
        return orderService.getOrderById(id);
    }

    /**
     *
     * @param orderNumber
     * @return
     */
    @QueryMapping
    public Mono<OrderDetails> getOrderByNumber(@Argument String orderNumber) {
        return orderService.getOrderByNumber(orderNumber);
    }


    @GraphQLResolver(field = "ordersByCustomer")
    public Flux<OrderDetails> getOrdersByCustomer(DataFetchingEnvironment env) {
        String customerId = env.getArgument("customerId");
        Integer limit = env.getArgumentOrDefault("limit", 10);
        Integer offset = env.getArgumentOrDefault("offset", 0);
        return orderService.getOrdersByCustomer(customerId, limit, offset);
    }

    @GraphQLResolver(field = "ordersByStatus")
    public Flux<OrderDetails> getOrdersByStatus(DataFetchingEnvironment env) {

        OrderStatus status = OrderStatus.valueOf(env.getArgument("status"));
        Integer limit = env.getArgumentOrDefault("limit", 10);
        Integer offset = env.getArgumentOrDefault("offset", 0);

        return orderService.getOrdersByStatus(status, limit, offset);
    }

   // @GraphQLResolver(field = "createOrder")
   @MutationMapping
    public Mono<OrderDetails> createOrder(@Argument CreateOrderInput input) {
        //CreateOrderInput input = env.getArgument("input");
        return orderService.createOrder(input);
    }

    @GraphQLResolver(field = "updateOrderStatus")
    public Mono<OrderDetails> updateOrderStatus(DataFetchingEnvironment env) {
        UpdateOrderStatusInput input = env.getArgument("input");
        return orderService.updateOrderStatus(input);
    }

    @GraphQLResolver(field = "addOrderNote")
    public Mono<OrderDetails> addOrderNote(DataFetchingEnvironment env) {
        AddOrderNoteInput input = env.getArgument("input");
        return orderService.addOrderNote(input);
    }
}
