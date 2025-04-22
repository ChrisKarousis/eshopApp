package com.ckarousis.eshopapp.services;

import com.ckarousis.eshopapp.model.*;
import com.ckarousis.eshopapp.repository.OrderItemRepository;
import com.ckarousis.eshopapp.repository.OrderRepository;
import com.ckarousis.eshopapp.repository.ProductRepository;
import com.ckarousis.eshopapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImp implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;
    public Order createOrder(OrderRequest orderRequest){
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Order order = new Order();
        order.setUser(user);
        order.setStatus("PENDING");
        int total = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for(OrderItemRequest item : orderRequest.getItems()){
            Long productId = item.getProductId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            int quantity = item.getQuantity();
            total += ((product.getPrice())*quantity);
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(quantity);
            orderItem.setSubtotal((product.getPrice())*quantity);
            orderItem.setProduct(product);
            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        }

        order.setTotalPrice(total);
        order.setItems(orderItems);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUserId(Long userId){
        List<Order> orders = orderRepository.findByUserId(userId);

        // If no orders are found, throw an exception
        if (orders.isEmpty()) {
            throw new RuntimeException("Orders not found");
        }

        return orders;
    }

    public Order updateOrderStatus(Long id, String status){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
