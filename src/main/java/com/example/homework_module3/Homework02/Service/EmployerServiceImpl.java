package com.example.homework_module3.Homework02.Service;

import com.example.homework_module3.Homework02.DAO.EmployerDaoImpl;
import com.example.homework_module3.Homework02.domain.Employer;
import com.example.homework_module3.Homework02.dto.EmployerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class EmployerServiceImpl implements EmployerService {

    private final EmployerDaoImpl employerDao;

    @Autowired
    public EmployerServiceImpl(EmployerDaoImpl employerDao) {
        this.employerDao = employerDao;
    }

    @Override
    public Employer save(Employer employer) {
        return employerDao.save(employer);
    }

    @Override
    public boolean delete(Employer employer) {
        return employerDao.delete(employer);
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
        return employerDao.deleteById(id);
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
    public Employer update(Long id, EmployerDto employerDto) {
        return employerDao.update(id, employerDto);
    }

    @Override
    public void deleteEmployer(Long id) {
        if (!employerDao.deleteById(id)) {
            throw new IllegalArgumentException("Employer not found with ID: " + id);
        }
    }
}
