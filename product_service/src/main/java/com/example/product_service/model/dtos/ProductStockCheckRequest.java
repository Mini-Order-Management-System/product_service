package com.example.product_service.model.dtos;

import lombok.Data;

@Data
public class ProductStockCheckRequest {
    private String productId;
    private int quantity;
}
