package com.nikitapopov.librarymvcboot.repositories;

import com.nikitapopov.librarymvcboot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    public Optional<Person> findByFullName(String fullName);
}
