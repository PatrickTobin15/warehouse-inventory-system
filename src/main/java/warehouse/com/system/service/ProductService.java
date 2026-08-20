package com.warehouse.system.service;

import com.warehouse.system.entity.Product;
import com.warehouse.system.exception.InvalidInputException;
import com.warehouse.system.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        if (product.getName() == null || product.getName().isBlank()) {
            throw new InvalidInputException("Product name is required");
        }
        if (product.getPrice() < 0) {
            throw new InvalidInputException("Product price cannot be negative");
        }
        if (product.getStock() < 0) {
            throw new InvalidInputException("Product stock cannot be negative");
        }
        return productRepository.save(product);
    }

    // "by" comes straight from the ?by= query param
    public List<Product> getSortedProducts(String by) {
        List<Product> products = productRepository.findAll();

        if (by == null) {
            throw new InvalidInputException("Missing 'by' query param. Use 'price' or 'stock'.");
        }

        switch (by.toLowerCase()) {
            case "price" -> SortUtil.insertionSort(products, Comparator.comparingDouble(Product::getPrice));
            case "stock" -> SortUtil.insertionSort(products, Comparator.comparingInt(Product::getStock));
            default -> throw new InvalidInputException("Unknown sort field '" + by + "'. Use 'price' or 'stock'.");
        }

        return products;
    }
}
