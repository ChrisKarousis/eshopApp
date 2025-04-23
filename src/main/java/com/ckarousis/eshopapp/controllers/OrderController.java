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
import java.util.ArrayList;
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
    public List<Order> getOrder(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);

        // If no orders are found, throw an exception
        if (orders.isEmpty()) {
            throw new RuntimeException("Orders not found");
        }

        return orders;
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

    /*@GetMapping("/user/{username}")
    public List<Order> getOrdersForCurrentUser(@PathVariable String username) {
        System.out.println("Username:" + username);
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Order> orders = orderService.getOrdersByUserId(user.getId());

        // If no orders are found, throw an exception
        if (orders.isEmpty()) {
            throw new RuntimeException("Orders not found");
        }

        return orders;
    }*/

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getOrdersByUsername(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            List<Order> orders = orderService.getOrdersByUserId(user.getId());
            System.out.println(orders);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching orders");
        }
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<String> completeOrder(@PathVariable Long id) {
        boolean success = orderService.completeOrder(id);
        if (success) {
            return ResponseEntity.ok("Order marked as completed");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found or already completed");
        }
    }


    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
