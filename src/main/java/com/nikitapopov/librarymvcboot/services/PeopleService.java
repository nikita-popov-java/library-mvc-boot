package com.nikitapopov.librarymvcboot.services;

import com.nikitapopov.librarymvcboot.models.Book;
import com.nikitapopov.librarymvcboot.models.Person;
import com.nikitapopov.librarymvcboot.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person find(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    public Person find(int id, boolean isNeedInitBooks) {
        Person person = find(id);
        if (isNeedInitBooks) {
            List<Book> books = person.getBooks();

            Hibernate.initialize(books);

            books.forEach(book -> {
                long currentTime = Calendar.getInstance().getTime().getTime();
                long overdueTime = 1000 * 60 * 60 * 24 * 10;
                long usedTime = book.getReceiptDate().getTime();

                book.setOverdue(Math.abs(usedTime - currentTime) > overdueTime);
            });
        }
        return person;
    }

    public Optional<Person> findByFullName(String fullName) {
        return peopleRepository.findByFullName(fullName);
    }

    @Transactional
    public Person save(Person person) {
        return peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}