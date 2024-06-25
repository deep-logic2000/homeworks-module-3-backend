package com.example.homework_module3.Homework03.DAO;

import com.example.homework_module3.Homework03.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<Customer, Long> {
}
