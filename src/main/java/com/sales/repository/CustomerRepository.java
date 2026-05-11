package com.sales.repository;

import com.sales.entity.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

    // Tìm khách hàng theo số điện thoại chính xác
    public Customer findByPhone(String phone) {
        return find("phone", phone).firstResult();
    }
}