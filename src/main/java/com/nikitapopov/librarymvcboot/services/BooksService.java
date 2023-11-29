package com.nikitapopov.librarymvcboot.services;

import com.nikitapopov.librarymvcboot.models.Book;
import com.nikitapopov.librarymvcboot.models.Person;
import com.nikitapopov.librarymvcboot.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public List<Book> findAllPageable(Integer page, Integer booksPerPage, boolean sort) {
        if (page != null && booksPerPage != null && page > 0 && booksPerPage > 1) {
            PageRequest request = PageRequest.of(page - 1, booksPerPage);
            if (sort) {
                request = request.withSort(Sort.by("yearOfCreated"));
            }
            return booksRepository.findAll(request).stream().toList();
        } else if (sort) {
            return booksRepository.findAll(Sort.by("yearOfCreated")).stream().toList();
        } else {
            return booksRepository.findAll().stream().toList();
        }
    }

    public Book find(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    public List<Book> findAllByNameIgnoreCaseStartingWith(String queryString) {
        return booksRepository.findAllByNameIgnoreCaseStartingWith(queryString);
    }

    @Transactional
    public void save(Book book) {
        if (book.getHolder().getId() == 0)
            book.setHolder(null);
        book.setReceiptDate(new Date());

        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void setBookToUser(int bookId, Person holder) {
        Book book = find(bookId);
        book.setReceiptDate(new Date());
        book.setHolder(holder);
        holder.getBooks().add(book);
    }
}
