package com.nikitapopov.librarymvcboot.services;

import com.nikitapopov.librarymvcboot.models.User;
import com.nikitapopov.librarymvcboot.models.enums.Authority;
import com.nikitapopov.librarymvcboot.repositories.LibraryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LibraryUserService {

    private final LibraryUserRepository libraryUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LibraryUserService(LibraryUserRepository libraryUserRepository, PasswordEncoder passwordEncoder) {
        this.libraryUserRepository = libraryUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isUserExisted(String username) {
        return libraryUserRepository.findByUsername(username).isPresent();
    }

    @Transactional
    public void save(User user) {
        user.setAuthority(Authority.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        libraryUserRepository.save(user);
    }
}