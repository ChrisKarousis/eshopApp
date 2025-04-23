package com.ckarousis.eshopapp.services;

import com.ckarousis.eshopapp.model.Order;
import com.ckarousis.eshopapp.model.OrderRequest;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(OrderRequest orderRequest);
    List<Order> getAllOrders();
    List<Order> getOrdersByUserId(Long userId);
    Order updateOrderStatus(Long id, String status);
    boolean completeOrder(Long id);
    void deleteOrder(Long id);
}
