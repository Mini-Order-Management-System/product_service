package com.example.product_service.model.dtos;

import lombok.Data;

@Data
public class ProductStockUpdateRequest {
    private String productId;
    private int quantityChange;
}