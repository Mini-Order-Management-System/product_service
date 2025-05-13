package com.example.product_service.service;

import com.example.product_service.model.dtos.ProductStockCheckRequest;
import com.example.product_service.model.dtos.ProductStockCheckResponse;
import com.example.product_service.model.dtos.ProductStockUpdateRequest;
import com.example.product_service.model.entities.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(String productId);

    List<ProductStockCheckResponse> checkStockAvailability(List<ProductStockCheckRequest> requests);

    void updateStock(List<ProductStockUpdateRequest> requests);
}
