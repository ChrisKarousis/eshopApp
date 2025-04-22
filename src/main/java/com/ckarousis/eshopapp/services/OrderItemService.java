package com.ckarousis.eshopapp.services;

import com.ckarousis.eshopapp.model.OrderItem;
import com.ckarousis.eshopapp.model.OrderItemRequest;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    OrderItem createOrderItem(OrderItemRequest orderItemRequest);
    List<OrderItem> getAllOrderItems();
    Optional<OrderItem> getOrderItem(Long id);
    OrderItem updateQuantity(Long id, int quantity);
    void deleteOrderItem(Long id);
}
