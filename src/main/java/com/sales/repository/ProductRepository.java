package com.sales.repository;

import com.sales.entity.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    // Tìm kiếm sản phẩm theo tên (không phân biệt hoa thường)
    public List<Product> findByName(String name) {
        return list("lower(name) like lower(?1)", "%" + name + "%");
    }

    // Tìm các sản phẩm sắp hết hàng (số lượng < 5)
    public List<Product> findLowStock() {
        return list("stockQuantity < ?1", 5);
    }
}