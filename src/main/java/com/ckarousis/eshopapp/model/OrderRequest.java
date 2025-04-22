package com.ckarousis.eshopapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Long userId;
    private List<OrderItemRequest> items;
}
