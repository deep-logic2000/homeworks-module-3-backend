package com.example.homework_module3.Homework05.Controller;

import com.example.homework_module3.Homework05.Service.EmployerServiceImpl;
import com.example.homework_module3.Homework05.Service.mappers.EmployerDtoMapperRequest;
import com.example.homework_module3.Homework05.Service.mappers.EmployerDtoMapperResponse;
import com.example.homework_module3.Homework05.domain.Employer;
import com.example.homework_module3.Homework05.domain.dto.EmployerDtoRequest;
import com.example.homework_module3.Homework05.domain.dto.EmployerDtoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employers")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@RequiredArgsConstructor
public class EmployerController {

    private final EmployerServiceImpl employerServiceImpl;
    private final EmployerDtoMapperRequest employerDtoMapperRequest;
    private final EmployerDtoMapperResponse employerDtoMapperResponse;


    @GetMapping
    public List<Employer> getAll() {
        log.info("getAll method in EmployerController is triggered.");
        return employerServiceImpl.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        log.info("getById method in EmployerController is triggered.");
        Employer employer = employerServiceImpl.getOne(id);
        if (employer == null) {
            log.warn("Employer with id {} was not found", id);
            return ResponseEntity.badRequest().body(String.format("Employer with id %d was not found", id));
        }
        return ResponseEntity.ok(employer);
    }

    @PostMapping
    public ResponseEntity<?> createEmployer(@Valid @RequestBody EmployerDtoRequest employerDtoRequest) {
        log.info("createEmployer method in EmployerController is triggered.");
        Employer newEmployer = new Employer(employerDtoRequest.getCompanyName(), employerDtoRequest.getCompanyAddress());
        Employer createdEmployer = employerServiceImpl.createEmployer(newEmployer);
        return ResponseEntity.ok(createdEmployer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployer(@PathVariable Long id, @Valid @RequestBody EmployerDtoRequest employerDtoRequest) {
        log.info("updateEmployer method in EmployerController is triggered.");
        Employer updatedEmployerEntity = employerDtoMapperRequest.convertToEntity(employerDtoRequest);
        updatedEmployerEntity.setId(id);
        try {
            Employer updatedEmployer = employerServiceImpl.update(updatedEmployerEntity);
            EmployerDtoResponse employerDtoResponse = employerDtoMapperResponse.convertToDto(updatedEmployer);
            return ResponseEntity.ok(employerDtoResponse);
        } catch (IllegalArgumentException e) {
            log.warn("Employer with id {} not found", id);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployer(@PathVariable Long id) {
        log.info("deleteEmployer method in EmployerController is triggered.");
        try {
            employerServiceImpl.deleteEmployer(id);
            return ResponseEntity.ok("Employer deleted successfully.");
        } catch (IllegalArgumentException e) {
            log.warn("Employer with id {} not found", id);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

