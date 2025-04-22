package com.ckarousis.eshopapp.controllers;

import com.ckarousis.eshopapp.model.Category;
import com.ckarousis.eshopapp.model.OrderItem;
import com.ckarousis.eshopapp.model.OrderItemRequest;
import com.ckarousis.eshopapp.services.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eshop/orderItems")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public List<OrderItem> getAllOrderItems() {
        return orderItemService.getAllOrderItems();
    }

    @GetMapping("/{id}")
    public OrderItem getOrderItem(@PathVariable Long id) {
        return orderItemService.getOrderItem(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));
    }

    @PostMapping
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItemRequest orderItemRequest) {
        OrderItem savedItem = orderItemService.createOrderItem(orderItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }


    @DeleteMapping("/{id}")
    public void deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
    }
}
