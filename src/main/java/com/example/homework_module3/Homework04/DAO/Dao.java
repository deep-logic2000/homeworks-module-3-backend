package com.example.homework_module3.Homework04.DAO;

import java.util.List;

public interface Dao<T> {
        T save(T obj);
        boolean delete(T obj);
        void deleteAll(List<T> entities);
        void saveAll(List<T> entities);
        List<T> findAll();
        boolean deleteById(long id);
        T getOne(Long id);
}
