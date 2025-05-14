package com.example.product_service.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductStockCheckResponse {
    private String productId;
    private boolean sufficientStock;
}