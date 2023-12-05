package com.nikitapopov.librarymvcboot.controllers;

import com.nikitapopov.librarymvcboot.models.User;
import com.nikitapopov.librarymvcboot.security.LibraryUserDetails;
import com.nikitapopov.librarymvcboot.services.LibraryUserService;
import com.nikitapopov.librarymvcboot.utils.UsersValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private final UsersValidator usersValidator;
    private final LibraryUserService libraryUserService;

    @Autowired
    public AuthenticationController(UsersValidator usersValidator, LibraryUserService libraryUserService) {
        this.usersValidator = usersValidator;
        this.libraryUserService = libraryUserService;
    }

    @GetMapping("/login")
    public String loginPage() {

        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {

        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") @Valid User user, BindingResult result) {

        usersValidator.validate(user, result);
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "auth/registration";
        }

        libraryUserService.save(user);

        return "redirect:/auth/login?success";
    }
}
