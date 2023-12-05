package com.nikitapopov.librarymvcboot.controllers;

import com.nikitapopov.librarymvcboot.models.Person;
import com.nikitapopov.librarymvcboot.services.PeopleService;
import com.nikitapopov.librarymvcboot.utils.PeopleValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.LinkedList;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final PeopleValidator peopleValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PeopleValidator peopleValidator) {
        this.peopleService = peopleService;
        this.peopleValidator = peopleValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());

        return "people/index";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("person") Person person) {

        return "people/new";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {

        Person person = peopleService.find(id, true);

        model.addAttribute("person", person);
        model.addAttribute("currentYear", Calendar.getInstance().get(Calendar.YEAR));
        model.addAttribute("books", person.getBooks());

        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {

        model.addAttribute("person", peopleService.find(id));

        return "people/edit";
    }

    @PostMapping()
    public String save(@ModelAttribute("person") @Valid Person person, BindingResult result) {

        peopleValidator.validate(person, result);
        if (result.hasErrors())
            return "people/new";

        Person savedPerson = peopleService.save(person);

        return "redirect:/people/" + savedPerson.getId();
    }

    @PatchMapping("/{id}")
    public String change(@PathVariable("id") int id,
                         @ModelAttribute("person") @Valid Person person,
                         BindingResult result) {

        peopleValidator.validate(person, result);
        if (result.hasErrors())
            return "people/edit";

        peopleService.update(id, person);

        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {

        peopleService.delete(id);

        return "redirect:/people";
    }
}