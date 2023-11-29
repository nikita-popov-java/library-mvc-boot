package com.nikitapopov.librarymvcboot.dao;

import com.nikitapopov.librarymvcboot.models.Person;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO extends AbstractLibraryDAO<Person> {

    @Autowired
    public PersonDAO(EntityManagerFactory factory) {
        super(factory);
    }

    @Override
    public List<Person> index() {
        Session session = factory.unwrap(Session.class);

        throw new IllegalArgumentException();
    }

    @Override
    public Optional<Person> show(String name) {
        Session session = factory.unwrap(Session.class);

        throw new IllegalArgumentException();
    }

    @Override
    public Person show(int id) {
        Session session = factory.unwrap(Session.class);

        throw new IllegalArgumentException();
    }

    @Override
    public void save(Person record) {
        Session session = factory.unwrap(Session.class);
    }

    @Override
    public void update(int id, Person record) {
        Session session = factory.unwrap(Session.class);
    }

    @Override
    public void delete(int id) {
        Session session = factory.unwrap(Session.class);
    }
}