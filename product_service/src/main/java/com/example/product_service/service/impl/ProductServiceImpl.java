package com.example.product_service.service.impl;

import com.example.product_service.model.dtos.ProductStockCheckRequest;
import com.example.product_service.model.dtos.ProductStockCheckResponse;
import com.example.product_service.model.dtos.ProductStockUpdateRequest;
import com.example.product_service.model.entities.Product;
import com.example.product_service.repository.ProductRepository;
import com.example.product_service.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product getProductById(String productId) {
        log.debug("Fetching product with ID: {}", productId);
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));
    }

    @Override
    public List<ProductStockCheckResponse> checkStockAvailability(List<ProductStockCheckRequest> requests) {
        log.debug("Checking stock availability for {} products.", requests.size());
        List<ProductStockCheckResponse> responses = new ArrayList<>();
        for (ProductStockCheckRequest req : requests) {
            Product product = this.getProductById(req.getProductId());
            boolean sufficient = product.getStockQuantity() >= req.getQuantity();
            responses.add(new ProductStockCheckResponse(req.getProductId(), sufficient));
        }
        log.debug("Stock check result: {}", responses);
        return responses;
    }

    @Override
    @Transactional
    public void updateStock(List<ProductStockUpdateRequest> requests) {
        log.debug("Updating stock for {} products.", requests.size());
        for (ProductStockUpdateRequest req : requests) {
            Product product = this.getProductById(req.getProductId());
            int beforeUpdateStock = product.getStockQuantity();
            int newQuantity = product.getStockQuantity() + req.getQuantityChange();
            if (newQuantity < 0) {
                log.error("Cannot update stock for {} as it will result in negative stock.", req.getProductId());
                throw new IllegalArgumentException("Cannot reduce stock below 0 for product: " + req.getProductId());
            }
            product.setStockQuantity(newQuantity);
            productRepository.save(product);

            log.info("Stock updated for product {}: {} -> {}", req.getProductId(), beforeUpdateStock, newQuantity);
        }
    }
}
