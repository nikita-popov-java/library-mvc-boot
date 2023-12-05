package com.nikitapopov.librarymvcboot.utils;

import com.nikitapopov.librarymvcboot.models.User;
import com.nikitapopov.librarymvcboot.services.LibraryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UsersValidator implements Validator {

    private final LibraryUserService libraryUserService;

    @Autowired
    public UsersValidator(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (libraryUserService.isUserExisted(user.getUsername())) {
            errors.rejectValue("username", "", "Пользователь с таким именем уже существует!");
        }
    }
}
