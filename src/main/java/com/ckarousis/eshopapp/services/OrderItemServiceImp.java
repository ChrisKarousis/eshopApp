package com.ckarousis.eshopapp.services;

import com.ckarousis.eshopapp.model.OrderItem;
import com.ckarousis.eshopapp.model.OrderItemRequest;
import com.ckarousis.eshopapp.model.Product;
import com.ckarousis.eshopapp.repository.OrderItemRepository;
import com.ckarousis.eshopapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImp implements OrderItemService{
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;

    public OrderItem createOrderItem(OrderItemRequest orderItemRequest){
        Product product = productRepository.findById(orderItemRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        //Order order = orderRepository.findById(request.getOrderId())
        //        .orElseThrow(() -> new RuntimeException("Order not found"));
        OrderItem orderItem = new OrderItem();
        //orderItem.setId(orderItemRequest.getOrderId());
        orderItem.setProduct(product);
        orderItem.setQuantity(orderItemRequest.getQuantity());
        orderItem.setSubtotal(orderItemRequest.getQuantity()*product.getPrice());
        return orderItemRepository.save(orderItem);

    }

    public List<OrderItem> getAllOrderItems(){
        return orderItemRepository.findAll();
    }

    public Optional<OrderItem> getOrderItem(Long id){
        return orderItemRepository.findById(id);
    }

    public OrderItem updateQuantity(Long id, int quantity){
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));
        orderItem.setQuantity(quantity);
        return orderItem;
    }

    public void deleteOrderItem(Long id){
        orderItemRepository.deleteById(id);
    }
}
