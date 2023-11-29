package com.nikitapopov.librarymvcboot.utils;

import com.nikitapopov.librarymvcboot.models.Book;
import com.nikitapopov.librarymvcboot.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BooksValidator implements Validator {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksValidator(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Book.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        Book existingBook = booksService.find(book.getId());

        if (book.getHolder().getId() != 0
                && peopleService.find(book.getHolder().getId()) == null) {
            errors.rejectValue("holder.id", "", "Пользователя с таким ID не существует!");
        }

        if (existingBook != null
                && existingBook.getName().equals(book.getName())
                && existingBook.getAuthor().equals(book.getAuthor())) {
            errors.rejectValue("name", "", "Такая книга уже существует!");
        }
    }
}
