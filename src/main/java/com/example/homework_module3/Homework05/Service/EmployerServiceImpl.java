package com.example.homework_module3.Homework05.Service;

import com.example.homework_module3.Homework05.DAO.EmployersJpaRepository;
import com.example.homework_module3.Homework05.domain.Employer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class EmployerServiceImpl implements EmployerService {

    private final EmployersJpaRepository employerDao;
    @Autowired
    public EmployerServiceImpl(EmployersJpaRepository employerDao) {
        this.employerDao = employerDao;
    }

    @Override
    public Employer save(Employer employer) {
        return employerDao.save(employer);
    }

    @Override
    public boolean delete(Employer employer) {
        if (employerDao.existsById(employer.getId())) {
            employerDao.delete(employer);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteAll(List<Employer> entities) {
        employerDao.deleteAll(entities);
    }

    @Override
    public void saveAll(List<Employer> entities) {
        employerDao.saveAll(entities);
    }

    @Override
    public List<Employer> findAll() {
        return employerDao.findAll();
    }

    @Override
    public boolean deleteById(long id) {
        if (employerDao.existsById(id)) {
            employerDao.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Employer getOne(Long id) {
        return employerDao.getOne(id);
    }

    @Override
    public Employer createEmployer(Employer employer) {
        return employerDao.save(employer);
    }

    @Override
    public Employer update(Employer employer) {
        return employerDao.save(employer);
    }

    @Override
    public void deleteEmployer(Long id) {
        employerDao.deleteById(id);
    }
}
