package com.nikitapopov.librarymvcboot.utils;

import com.nikitapopov.librarymvcboot.models.Person;
import com.nikitapopov.librarymvcboot.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PeopleValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PeopleValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Person.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> existingPerson = peopleService.findByFullName(person.getFullName());


        if (existingPerson.isPresent()
                && existingPerson.get().getId() != person.getId()) {
            errors.rejectValue("fullName", "", "Такой пользователь уже существует!");
        }
    }
}
