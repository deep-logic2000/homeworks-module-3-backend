package com.example.homework_module3.Homework02.Controller;

import com.example.homework_module3.Homework02.Service.EmployerServiceImpl;
import com.example.homework_module3.Homework02.domain.Employer;
import com.example.homework_module3.Homework02.dto.EmployerDto;
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
    public ResponseEntity<?> createEmployer(@RequestBody EmployerDto employerDto) {
        if (employerDto.getName() == null || employerDto.getAddress() == null) {
            return ResponseEntity.badRequest().body("Name and address are required.");
        }

        Employer newEmployer = new Employer(employerDto.getName(), employerDto.getAddress());
        Employer createdEmployer = employerServiceImpl.createEmployer(newEmployer);
        log.info("created employer: " + createdEmployer);
        return ResponseEntity.ok(createdEmployer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployer(@PathVariable Long id, @RequestBody EmployerDto employerDto) {
        try {
            Employer updatedEmployer = employerServiceImpl.update(id, employerDto);
            return ResponseEntity.ok(updatedEmployer);
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

