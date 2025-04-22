package com.ckarousis.eshopapp.controllers;

import com.ckarousis.eshopapp.model.Order;
import com.ckarousis.eshopapp.model.OrderItem;
import com.ckarousis.eshopapp.model.OrderItemRequest;
import com.ckarousis.eshopapp.model.OrderRequest;
import com.ckarousis.eshopapp.services.OrderItemService;
import com.ckarousis.eshopapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eshop/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{userId}") //gets the orders by UserId
    public Order getOrder(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        Order savedOrder = orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }


    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
