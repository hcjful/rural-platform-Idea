package com.rural.platform.service;

import com.rural.platform.entity.Product;
import com.rural.platform.page.Page;

import java.util.List;

public interface ProductService {
    Product getProductById(Long id);
    Page getAllProducts(Integer pageNum, Integer pageSize, String category, String search);
    List<Product> getProductsByCategory(String category);
    List<Product> searchProducts(String keyword);
    Product createProduct(Product product);
    boolean updateProductStock(Long id, Integer stock);
    boolean deleteProduct(Long id);
}

