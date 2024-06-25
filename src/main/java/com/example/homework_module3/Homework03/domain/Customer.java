package com.example.homework_module3.Homework03.domain;

import com.example.homework_module3.Homework03.domain.AbstractEntity;
import com.example.homework_module3.Homework03.domain.Account;
import com.example.homework_module3.Homework03.domain.Employer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.*;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends AbstractEntity {

    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Integer age;

    //    @JoinColumn(name = "customer_id")
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Account> accounts = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "customer_employer", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "employer_id"))
    private Set<Employer> employers = new HashSet<>();

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @CreatedDate
    @Temporal(TIMESTAMP)
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    @Column(name = "last_modified_date", updatable = false)
    private LocalDateTime lastModifiedDate;

    public Customer(String name, String email, Integer age, String password, String phoneNumber, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public Customer(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }


    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void addEmployer(Employer employer) {
        employers.add(employer);
        employer.getCustomers().add(this);
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + getId() + ", name='" + name + '\'' + ", email='" + email + '\'' + ", age=" + age + ", password='" + password + '\'' + ", phoneNumber='" + phoneNumber + '\'' + ", accounts=" + accounts +
//                ", employers=" + employers +
                '}';
    }

}
