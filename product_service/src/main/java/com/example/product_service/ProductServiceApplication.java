package com.example.product_service;

import com.example.product_service.model.entities.Product;
import com.example.product_service.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@Slf4j
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
		log.info("Product Service Application started!");
	}

	@Bean
	CommandLineRunner init(ProductRepository productRepository) {
		return args -> {
			log.info("Initializing sample products...");

			List<Product> products = List.of(
					new Product("P001", "Product A", 100.0, 50),
					new Product("P002", "Product B", 150.0, 30),
					new Product("P003", "Product C", 200.0, 20),
					new Product("P004", "Product D", 250.0, 10),
					new Product("P005", "Product E", 300.0, 5)
			);

			productRepository.saveAll(products);

			log.info("Products initialized successfully!");
		};
	}
}
