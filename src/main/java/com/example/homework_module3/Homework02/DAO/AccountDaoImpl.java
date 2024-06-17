package com.example.homework_module3.Homework02.DAO;

import com.example.homework_module3.Homework02.domain.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class AccountDaoImpl implements Dao<Account> {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;


    @Override
    public Account save(Account account) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        try {
            if (account.getId() == null) {
                em.persist(account);
            } else {
                account = em.merge(account);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
        return account;
    }

    @Override
    public boolean delete(Account account) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        try {
            Account managedAccount = em.find(Account.class, account.getId());
            if (managedAccount != null) {
                em.remove(managedAccount);
                em.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
        return false;
    }

    @Override
    public void deleteAll(List<Account> entities) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        try {
            for (Account entity : entities) {
                Account managedAccount = em.find(Account.class, entity.getId());
                if (managedAccount != null) {
                    em.remove(managedAccount);
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void saveAll(List<Account> entities) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        try {
            for (Account entity : entities) {
                if (entity.getId() == null) {
                    em.persist(entity);
                } else {
                    em.merge(entity);
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Account> findAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Account> accounts;
        try {
            Query query = em.createQuery("SELECT a FROM Account a", Account.class);
            accounts = query.getResultList();
        } finally {
            em.close();
        }
        return accounts;
    }

    @Override
    public boolean deleteById(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        try {
            Account account = em.find(Account.class, id);
            if (account != null) {
                em.remove(account);
                em.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
        return false;
    }

    @Override
    public Account getOne(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Account account = null;
        try {
            account = em.find(Account.class, id);
            log.info("Account in getOne: {}", account);

        } catch (Exception e) {
            log.error("Error finding account by ID", e);
        } finally {
            em.close();
        }
        return account;
    }

    public Account findById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Account account;
        try {
            account = em.find(Account.class, id);
        } finally {
            em.close();
        }
        return account;
    }

    public Account findByNumber(String number) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Account account;
        try {
            Query query = em.createQuery("SELECT a FROM Account a WHERE a.number = :number", Account.class);
            query.setParameter("number", number);
            account = (Account) query.getSingleResult();
        } finally {
            em.close();
        }
        return account;
    }
}
