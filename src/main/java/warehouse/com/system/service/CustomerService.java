package com.warehouse.system.service;

import com.warehouse.system.entity.Customer;
import com.warehouse.system.exception.InvalidInputException;
import com.warehouse.system.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer) {
        if (customer.getName() == null || customer.getName().isBlank()) {
            throw new InvalidInputException("Customer name is required");
        }
        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            throw new InvalidInputException("Customer email is required");
        }
        return customerRepository.save(customer);
    }
}
