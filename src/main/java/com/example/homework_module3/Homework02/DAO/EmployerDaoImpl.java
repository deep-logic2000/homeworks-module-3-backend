package com.example.homework_module3.Homework02.DAO;

import com.example.homework_module3.Homework02.domain.Employer;
import com.example.homework_module3.Homework02.dto.EmployerDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class EmployerDaoImpl implements Dao<Employer> {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Employer save(Employer employer) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Employer persistedEmployer;
        try {
            if (employer.getId() == null) {
                em.persist(employer);
                persistedEmployer = employer;
            } else {
                persistedEmployer = em.find(Employer.class, employer.getId());
                if (persistedEmployer != null) {
                    persistedEmployer.setName(employer.getName());
                    persistedEmployer.setAddress(employer.getAddress());
                    persistedEmployer = em.merge(persistedEmployer);
                } else {
                    em.persist(employer);
                    persistedEmployer = employer;
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Error saving employer", e);
            throw e;
        } finally {
            em.close();
        }
        return persistedEmployer;
    }

    @Override
    public boolean delete(Employer employer) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        try {
            Employer managedEmployer = em.find(Employer.class, employer.getId());
            if (managedEmployer != null) {
                em.remove(managedEmployer);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Error deleting employer", e);
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteAll(List<Employer> entities) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        try {
            for (Employer entity : entities) {
                Employer managedEmployer = em.find(Employer.class, entity.getId());
                if (managedEmployer != null) {
                    em.remove(managedEmployer);
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Error deleting all employers", e);
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void saveAll(List<Employer> entities) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        try {
            for (Employer entity : entities) {
                if (entity.getId() == null) {
                    em.persist(entity);
                } else {
                    em.merge(entity);
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Error saving all employers", e);
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Employer> findAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Employer> employers;
        try {
            Query query = em.createQuery("SELECT e FROM Employer e", Employer.class);
            employers = query.getResultList();
        } finally {
            em.close();
        }
        return employers;
    }

    @Override
    public boolean deleteById(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        try {
            Employer employer = em.find(Employer.class, id);
            if (employer != null) {
                em.remove(employer);
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
    public Employer getOne(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Employer employer;
        try {
            employer = em.find(Employer.class, id);
        } finally {
            em.close();
        }
        return employer;
    }

    public Employer update(Long id, EmployerDto employerDto) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Employer existingEmployer;
        try {
            existingEmployer = em.find(Employer.class, id);
            if (existingEmployer == null) {
                throw new IllegalArgumentException("Employer not found with ID: " + id);
            }
            existingEmployer.setName(employerDto.getName());
            existingEmployer.setAddress(employerDto.getAddress());
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Error updating employer", e);
            throw e;
        } finally {
            em.close();
        }
        return existingEmployer;
    }
}

