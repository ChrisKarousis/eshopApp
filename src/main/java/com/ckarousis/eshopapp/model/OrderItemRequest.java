package com.ckarousis.eshopapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {
    private Long productId;
    private Long orderId;
    private int quantity;
}
