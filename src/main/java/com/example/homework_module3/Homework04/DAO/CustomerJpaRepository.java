package com.example.homework_module3.Homework04.DAO;

import com.example.homework_module3.Homework04.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerJpaRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomersByName(String name);
}
