package com.example.homework_module3.Homework02.Service;

import com.example.homework_module3.Homework02.domain.Employer;
import com.example.homework_module3.Homework02.dto.EmployerDto;

import java.util.List;

public interface EmployerService {
    Employer save(Employer employer);
    boolean delete(Employer employer);
    void deleteAll(List<Employer> entities);
    void saveAll(List<Employer> entities);
    List<Employer> findAll();
    boolean deleteById(long id);
    Employer getOne(Long id);
    Employer createEmployer(Employer employer);
    Employer update(Long id, EmployerDto employerDto);
    void deleteEmployer(Long id);
}
