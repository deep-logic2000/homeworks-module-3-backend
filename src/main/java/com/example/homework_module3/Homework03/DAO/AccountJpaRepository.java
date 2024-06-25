package com.example.homework_module3.Homework03.DAO;

import com.example.homework_module3.Homework03.domain.Account;
import com.example.homework_module3.Homework03.domain.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {

    Account findByNumber(String number);

//    @Query("select a from Account a")
//    List<Account> findAllFull();
//
//    @EntityGraph("employeesFull")
//    @Query("SELECT a FROM Account a WHERE a.number = :number")
//    Account findByFirstName(@Param("number") String number);
//
//    @EntityGraph("employeesFull")
//    @Query("SELECT a FROM Account a WHERE a.number = :number AND a.number = :number")
//    Account findByFirstNameAndDepartmentName(@Param("firstName") String firstName, @Param("departmentName") String departmentName);
}
