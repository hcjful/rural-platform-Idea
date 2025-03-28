package com.rural.platform.service;

import com.rural.platform.entity.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> searchProducts(String keyword);
    Product createProduct(Product product);
    boolean updateProductStock(Long id, Integer stock);
    boolean deleteProduct(Long id);
}

