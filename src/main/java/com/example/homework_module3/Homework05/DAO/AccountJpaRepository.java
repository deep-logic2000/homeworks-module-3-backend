package com.example.homework_module3.Homework05.DAO;

import com.example.homework_module3.Homework05.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

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
