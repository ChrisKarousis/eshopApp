package com.ckarousis.eshopapp.controllers;

import com.ckarousis.eshopapp.model.*;
import com.ckarousis.eshopapp.services.OrderItemService;
import com.ckarousis.eshopapp.services.OrderService;
import com.ckarousis.eshopapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/eshop/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{userId}") //gets the orders by UserId
    public Order getOrder(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // Order post looks like this
    // OrderItem is created automatically
    // {
    //    "userId": 8,
    //    "items":[{"productId":12, "quantity":33},
    //    {"productId":15, "quantity":21}
    //    ]
    //}
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        Order savedOrder = orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @GetMapping("/user/{username}")
    public Optional<Order> getOrdersForCurrentUser(@PathVariable String username) {
        System.out.println("Username:" + username);
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderService.getOrdersByUserId(user.getId());
    }


    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
