package com.nikitapopov.librarymvcboot.repositories;

import com.nikitapopov.librarymvcboot.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    public List<Book> findAllByNameIgnoreCaseStartingWith(String name);
}
