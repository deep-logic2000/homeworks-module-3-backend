package com.example.homework_module3.Homework02.DAO;

import com.example.homework_module3.Homework02.domain.Account;
import com.example.homework_module3.Homework02.domain.Customer;
import com.example.homework_module3.Homework02.dto.AccountDto;
import com.example.homework_module3.Homework02.dto.CustomerDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.Query;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Data
@Slf4j
public class CustomerDaoImpl implements Dao<Customer> {
    //    private List<Customer> customers;
//    private Long currentId = 0L;
//    @JsonIgnore
    private Dao<Account> accountDao;
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public CustomerDaoImpl(Dao<Account> accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Customer save(Customer customer) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Customer persistedCustomer;
        try {
            if (customer.getId() == null) {
                em.persist(customer);
                persistedCustomer = customer;
            } else {
                persistedCustomer = em.find(Customer.class, customer.getId());
                if (persistedCustomer != null) {
                    persistedCustomer.setName(customer.getName());
                    persistedCustomer.setEmail(customer.getEmail());
                    persistedCustomer.setAge(customer.getAge());
                    persistedCustomer.setAccounts(customer.getAccounts());
                    persistedCustomer = em.merge(persistedCustomer);
                } else {
                    // If the customer does not exist, we should create a new one
                    em.persist(customer);
                    persistedCustomer = customer;
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Error saving customer", e);
            throw e;
        } finally {
            em.close();
        }
        return persistedCustomer;
    }

    @Override
    public boolean delete(Customer customer) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        try {
            Customer managedCustomer = em.find(Customer.class, customer.getId());
            if (managedCustomer != null) {
                em.remove(managedCustomer);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Error deleting customer", e);
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteAll(List<Customer> entities) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        try {
            for (Customer entity : entities) {
                Customer managedCustomer = em.find(Customer.class, entity.getId());
                if (managedCustomer != null) {
                    em.remove(managedCustomer);
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Error deleting all customers", e);
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void saveAll(List<Customer> entities) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        try {
            for (Customer entity : entities) {
                if (entity.getId() == null) {
                    em.persist(entity);
                } else {
                    em.merge(entity);
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Error saving all customers", e);
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Customer> findAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<Customer> customers = null;
        em.getTransaction().begin();
        try {
            Query query = em.createQuery("SELECT c FROM Customer c", Customer.class);
            customers = query.getResultList();
            log.info("Found customers: {}", customers);
        } catch (Exception e) {
            log.error("Error finding all customers", e);
        } finally {
            em.close();
        }
        log.info("customers in findAll: " + customers);
        return customers;
    }

    @Override
    public boolean deleteById(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        try {
            Customer managedCustomer = em.find(Customer.class, id);
            if (managedCustomer != null) {
                em.remove(managedCustomer);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Error deleting customer by ID", e);
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Customer getOne(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Customer customer = null;
        em.getTransaction().begin();
        try {
            customer = em.find(Customer.class, id);
            log.info("Customer in getOne: {}", customer);
        } catch (Exception e) {
            log.error("Error finding customer by ID", e);
        } finally {
            em.close();
        }
        return customer;
    }

    public Customer update(Long id, CustomerDto customerDto) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Customer existingCustomer = null;

        try {
            existingCustomer = em.find(Customer.class, id);
            log.info("existingCustomer in update: " + existingCustomer);
            if (existingCustomer == null) {
                throw new IllegalArgumentException("Customer not found with ID: " + id);
            }
            existingCustomer.setName(customerDto.getName());
            existingCustomer.setEmail(customerDto.getEmail());
            existingCustomer.setAge(customerDto.getAge());
            em.getTransaction().commit();
            log.info("existingCustomer update after update: " + existingCustomer);

        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Error updating customer", e);
            throw e;
        } finally {
            em.close();
        }
        return existingCustomer;
    }

    public Account addAccount(Long customerId, AccountDto accountDto) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Account newAccount = null;
        try {
            Customer customer = em.find(Customer.class, customerId);
            if (customer == null) {
                throw new IllegalArgumentException("Customer not found with ID: " + customerId);
            }
            if (accountDto.getBalance() < 0) {
                throw new IllegalArgumentException("Amount must be greater than zero.");
            }
            newAccount = new Account(accountDto.getCurrency());
            newAccount.setCustomer(customer);
            newAccount.setBalance(accountDto.getBalance());
            customer.addAccount(newAccount);
            em.persist(newAccount);
            em.merge(customer);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Error adding account", e);
            throw e;
        } finally {
            em.close();
        }
        return newAccount;
    }
}
