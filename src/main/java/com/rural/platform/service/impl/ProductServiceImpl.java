package com.rural.platform.service.impl;

import com.rural.platform.entity.Product;
import com.rural.platform.mapper.ProductMapper;
import com.rural.platform.service.ProductService;
import com.rural.platform.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product getProductById(Long id) {
        return productMapper.findById(id);
    }

    @Override
    public Page getAllProducts(Integer pageNum, Integer pageSize, String category, String search) {
        // 获取总记录数
        int totalNum = (int) productMapper.countProducts(category, search);
        
        // 创建分页对象
        Page page = new Page(pageNum, pageSize, totalNum, null);
        
        // 获取当前页数据
        List<Product> records = productMapper.findByPage(page.getLimitIndex(), pageSize, category, search);
        
        // 设置结果列表
        page.setResultList(records);
        
        return page;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productMapper.findByCategory(category);
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return productMapper.searchByKeyword(keyword);
    }

    @Override
    public Product createProduct(Product product) {
        productMapper.insert(product);
        return product;
    }

    @Override
    public boolean updateProductStock(Long id, Integer stock) {
        return productMapper.updateStock(id, stock) > 0;
    }

    @Override
    public boolean deleteProduct(Long id) {
        return productMapper.deleteById(id) > 0;
    }
}
