package com.example.product_service.controller;

import com.example.product_service.model.dtos.ProductStockCheckRequest;
import com.example.product_service.model.dtos.ProductStockCheckResponse;
import com.example.product_service.model.dtos.ProductStockUpdateRequest;
import com.example.product_service.model.entities.Product;
import com.example.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable String productId) {
        log.info("Request received: GET /api/products/{}", productId);
        try {
            Product product = productService.getProductById(productId);
            log.info("Product fetched successfully: {}", product);
            return ResponseEntity.ok(product);
        } catch (NoSuchElementException e) {
            log.error("Error fetching product: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/check-stock")
    public ResponseEntity<?> checkStock(@RequestBody List<ProductStockCheckRequest> requests) {
        log.info("Request received: POST /api/products/check-stock with body: {}", requests);
        try {
            List<ProductStockCheckResponse> responses = productService.checkStockAvailability(requests);
            log.info("Stock check result: {}", responses);
            return ResponseEntity.ok(responses);
        } catch (NoSuchElementException e) {
            log.error("Error checking stock: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/update-stock")
    public ResponseEntity<?> updateStock(@RequestBody List<ProductStockUpdateRequest> requests) {
        log.info("Request received: POST /api/products/update-stock with body: {}", requests);
        try {
            productService.updateStock(requests);
            log.info("Stock update successful.");
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException | IllegalArgumentException e) {
            log.error("Error updating stock: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
