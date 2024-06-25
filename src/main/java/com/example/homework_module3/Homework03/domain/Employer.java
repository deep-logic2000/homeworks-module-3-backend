package com.example.homework_module3.Homework03.domain;

import com.example.homework_module3.Homework03.domain.AbstractEntity;
import com.example.homework_module3.Homework03.domain.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="employers")
public class Employer extends AbstractEntity {
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @ManyToMany(mappedBy = "employers")
    private Set<Customer> customers = new HashSet<>();

    public Employer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        customer.getEmployers().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employer employer = (Employer) o;
        return Objects.equals(getId(), employer.getId()) && Objects.equals(name, employer.name) && Objects.equals(address, employer.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), name, address);
    }

    @Override
    public String toString() {
        return "Employer{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
//                ", customers=" + customers +
                '}';
    }
}
