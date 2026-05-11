package com.sales.service;

import com.sales.entity.Customer;
import com.sales.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.listAll();
    }

    @Transactional
    public void save(Customer customer) {
        customerRepository.persist(customer);
    }

    public List<Customer> search(String query) {
        return customerRepository.find("name like ?1 or phone like ?1", "%" + query + "%").list();
    }
}