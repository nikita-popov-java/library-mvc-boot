package com.nikitapopov.librarymvcboot.dao;

import com.nikitapopov.librarymvcboot.models.Book;
import com.nikitapopov.librarymvcboot.models.Person;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO extends AbstractLibraryDAO<Book> {

    @Autowired
    public BookDAO(EntityManagerFactory factory) {
        super(factory);
    }

    @Override
    public List<Book> index() {
        Session session = factory.unwrap(Session.class);

        throw new IllegalArgumentException();
    }

    @Override
    public Optional<Book> show(String name) {
        Session session = factory.unwrap(Session.class);

        throw new IllegalArgumentException();
    }

    @Override
    public Book show(int id) {
        Session session = factory.unwrap(Session.class);

        throw new IllegalArgumentException();
    }

    @Override
    public void save(Book record) {
        Session session = factory.unwrap(Session.class);
    }

    @Override
    public void update(int id, Book record) {
        Session session = factory.unwrap(Session.class);
    }

    @Override
    public void delete(int id) {
        Session session = factory.unwrap(Session.class);
    }

    public List<Book> getUserBooks(int id) {
        Session session = factory.unwrap(Session.class);

        throw new IllegalArgumentException();
    }

    public Person getBookHolder(int bookId) {
        Session session = factory.unwrap(Session.class);

        throw new IllegalArgumentException();
    }

    public void setBookToUser(int bookId, Integer userId) {
        Session session = factory.unwrap(Session.class);
    }
}
