package com.sales.service;

import com.sales.entity.Product;
import com.sales.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.listAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public void save(Product product) {
        productRepository.persist(product);
    }

    @Transactional
    public void update(Long id, Product product) {
        Product entity = productRepository.findById(id);
        if (entity != null) {
            entity.name = product.name;
            entity.price = product.price;
            entity.stockQuantity = product.stockQuantity;
        }
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> searchByName(String name) {
        return productRepository.findByName(name);
    }
}