package com.example.homework_module3.Homework04.Service;

import com.example.homework_module3.Homework04.DAO.EmployersJpaRepository;
import com.example.homework_module3.Homework04.domain.Employer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployerServiceImplTest {

    @Mock
    private EmployersJpaRepository employerDao;

    @InjectMocks
    private EmployerServiceImpl employerService;

    @Captor
    private ArgumentCaptor<Long> employerArgumentCaptor;


    @Test
    void save() {
        Employer employerToSave = new Employer("Company A", "Address A");
        Employer savedEmployer = new Employer("Company A", "Address A");
        savedEmployer.setId(1L);

        when(employerDao.save(any())).thenReturn(savedEmployer);

        Employer result = employerService.save(employerToSave);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Company A", result.getName());
        assertEquals("Address A", result.getAddress());

        verify(employerDao, times(1)).save(any());
        verifyNoMoreInteractions(employerDao);
    }

    @Test
    void delete() {
        Employer testEmployer = new Employer();
        Employer employerToDelete = new Employer("Company A", "Address A");
        employerToDelete.setId(1L);

        when(employerDao.existsById(employerToDelete.getId())).thenReturn(true);

        boolean result = employerService.delete(employerToDelete);

        assertTrue(result);

        verify(employerDao, times(1)).existsById(employerToDelete.getId());
        verify(employerDao, times(1)).delete(employerToDelete);
        verifyNoMoreInteractions(employerDao);
    }

    @Test
    void deleteEmployer_NotFound() {
        Employer employerToDelete = new Employer("Company A", "Address A");
        employerToDelete.setId(1L);

        when(employerDao.existsById(employerToDelete.getId())).thenReturn(false);

        boolean result = employerService.delete(employerToDelete);

        assertFalse(result);

        verify(employerDao, times(1)).existsById(employerToDelete.getId());
        verifyNoMoreInteractions(employerDao);
    }

    @Test
    void deleteAll() {
        List<Employer> employersToDelete = new ArrayList<>();
        Employer newEmployer1 = new Employer("Company A", "Address A");
        newEmployer1.setId(1L);
        Employer newEmployer2 = new Employer("Company B", "Address B");
        newEmployer1.setId(2L);

        employersToDelete.add(newEmployer1);
        employersToDelete.add(newEmployer2);

        employerService.deleteAll(employersToDelete);

        verify(employerDao, times(1)).deleteAll(employersToDelete);
        verifyNoMoreInteractions(employerDao);
    }

    @Test
    void saveAll() {
        List<Employer> employersToSave = new ArrayList<>();
        employersToSave.add(new Employer("Company A", "Address A"));
        employersToSave.add(new Employer("Company B", "Address B"));

        employerService.saveAll(employersToSave);

        verify(employerDao, times(1)).saveAll(employersToSave);
        verifyNoMoreInteractions(employerDao);
    }

    @Test
    void findAll() {
        List<Employer> expectedEmployers = new ArrayList<>();
        Employer employer1 = new Employer("Company A", "Address A");
        employer1.setId(1L);
        Employer employer2 = new Employer("Company B", "Address B");
        employer2.setId(2L);

        expectedEmployers.add(employer1);
        expectedEmployers.add(employer2);

        when(employerDao.findAll()).thenReturn(expectedEmployers);

        List<Employer> result = employerService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(employerDao, times(1)).findAll();
        verifyNoMoreInteractions(employerDao);
    }

    @Test
    void deleteById() {
        Long employerId = 1L;

        when(employerDao.existsById(employerId)).thenReturn(true);

        boolean result = employerService.deleteById(employerId);

        assertTrue(result);

        verify(employerDao, times(1)).existsById(employerId);
        verify(employerDao, times(1)).deleteById(employerId);
        verifyNoMoreInteractions(employerDao);
    }


    @Test
    void createEmployer() {
        Employer employerToCreate = new Employer("Company C", "Address C");
        Employer createdEmployer = new Employer("Company C", "Address C");
        createdEmployer.setId(1L);

        when(employerDao.save(any())).thenReturn(createdEmployer);

        Employer result = employerService.createEmployer(employerToCreate);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Company C", result.getName());
        assertEquals("Address C", result.getAddress());

        verify(employerDao, times(1)).save(any());
        verifyNoMoreInteractions(employerDao);
    }

    @Test
    void update() {
        Employer employerToUpdate = new Employer("Company D", "Address D");
        employerToUpdate.setId(1L);
        Employer updatedEmployer = new Employer("Company D", "Address D");
        updatedEmployer.setId(1L);

        when(employerDao.save(any())).thenReturn(updatedEmployer);

        Employer result = employerService.update(employerToUpdate);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Company D", result.getName());
        assertEquals("Address D", result.getAddress());

        verify(employerDao, times(1)).save(any());
        verifyNoMoreInteractions(employerDao);
    }

    @Test
    void deleteEmployer() {
        Long employerId = 1L;

        doThrow(new IllegalArgumentException("Employer not found")).when(employerDao).deleteById(employerId);

        assertThrows(IllegalArgumentException.class, () -> {
            employerService.deleteEmployer(employerId);
        });

        verify(employerDao, times(1)).deleteById(employerId);
        verifyNoMoreInteractions(employerDao);
    }
}