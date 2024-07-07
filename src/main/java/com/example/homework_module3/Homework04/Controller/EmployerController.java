package com.example.homework_module3.Homework04.Controller;

import com.example.homework_module3.Homework04.Service.EmployerServiceImpl;
import com.example.homework_module3.Homework04.Service.mappers.EmployerDtoMapperRequest;
import com.example.homework_module3.Homework04.Service.mappers.EmployerDtoMapperResponse;
import com.example.homework_module3.Homework04.domain.Employer;
import com.example.homework_module3.Homework04.domain.dto.EmployerDtoRequest;
import com.example.homework_module3.Homework04.domain.dto.EmployerDtoResponse;
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
        return employerServiceImpl.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Employer employer = employerServiceImpl.getOne(id);
        if (employer == null) {
            return ResponseEntity.badRequest().body(String.format("Employer with id %d was not found", id));
        }
        return ResponseEntity.ok(employer);
    }

    @PostMapping
    public ResponseEntity<?> createEmployer(@Valid @RequestBody EmployerDtoRequest employerDtoRequest) {

        Employer newEmployer = new Employer(employerDtoRequest.getCompanyName(), employerDtoRequest.getCompanyAddress());
        Employer createdEmployer = employerServiceImpl.createEmployer(newEmployer);
        return ResponseEntity.ok(createdEmployer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployer(@PathVariable Long id, @Valid @RequestBody EmployerDtoRequest employerDtoRequest) {
        Employer updatedEmployerEntity = employerDtoMapperRequest.convertToEntity(employerDtoRequest);
        updatedEmployerEntity.setId(id);
        try {
            Employer updatedEmployer = employerServiceImpl.update(updatedEmployerEntity);
            EmployerDtoResponse employerDtoResponse = employerDtoMapperResponse.convertToDto(updatedEmployer);
            return ResponseEntity.ok(employerDtoResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployer(@PathVariable Long id) {
        try {
            employerServiceImpl.deleteEmployer(id);
            return ResponseEntity.ok("Employer deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

