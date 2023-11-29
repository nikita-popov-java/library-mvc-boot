package com.nikitapopov.librarymvcboot.dao;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public abstract class AbstractLibraryDAO<T> {
    protected final EntityManagerFactory factory;

    public AbstractLibraryDAO(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public abstract List<T> index();
    public abstract Optional<T> show(String name);
    public abstract T show(int id);
    @Transactional
    public abstract void save(T record);
    @Transactional
    public abstract void update(int id, T record);
    @Transactional
    public abstract void delete(int id);
}
