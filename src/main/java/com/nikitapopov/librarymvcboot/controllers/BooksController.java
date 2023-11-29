package com.nikitapopov.librarymvcboot.controllers;

import com.nikitapopov.librarymvcboot.models.*;
import com.nikitapopov.librarymvcboot.services.*;
import com.nikitapopov.librarymvcboot.utils.BooksValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;
    private final BooksValidator booksValidator;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService, BooksValidator booksValidator) {
        this.booksService = booksService;
        this.peopleService = peopleService;
        this.booksValidator = booksValidator;
    }

    @GetMapping()
    public String index(@RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) Boolean sortByYear,
                        Model model) {

        List<Book> resultBooksList = (page == null && booksPerPage == null && sortByYear == null)
                ? booksService.findAll()
                : booksService.findAllPageable(page, booksPerPage, sortByYear);

        model.addAttribute("books", resultBooksList);

        return "books/index";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("book") Book book) {

        return "books/new";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {

        Book book = booksService.find(id);
        model.addAttribute("book", book);
        model.addAttribute("holder", book.isFree()
                ? new Person()
                : book.getHolder());

        if (book.isFree()) {
            model.addAttribute("people", peopleService.findAll());
        }

        return "books/show";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "query_string", required = false) String queryString,
                         Model model) {

        if (queryString != null) {
            model.addAttribute("foundBooks", booksService.findAllByNameIgnoreCaseStartingWith(queryString));
        }

        return "books/search";
    }



    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {

        model.addAttribute("book", booksService.find(id));

        return "books/edit";
    }

    @PostMapping()
    public String save(@ModelAttribute("book") @Valid Book book, BindingResult result) {
        booksValidator.validate(book, result);
        if (result.hasErrors()) {
            return "books/new";
        }
        booksService.save(book);

        return "redirect:/books";
    }

    @PutMapping("/{id}")
    public String change(@PathVariable("id") int id,
                         @ModelAttribute("book") @Valid Book book,
                         BindingResult result) {

        booksValidator.validate(book, result);
        if (result.hasErrors())
            return "books/edit";

        booksService.update(id, book);

        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String setHolder(@PathVariable("id") int id,
                            @RequestParam(value = "free", required = false) boolean free,
                            @ModelAttribute("holder") Person person) {

        if (booksService.find(id) != null) {
            booksService.setBookToUser(id, free ? null : person);
        }

        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {

        booksService.delete(id);

        return "redirect:/books";
    }
}
