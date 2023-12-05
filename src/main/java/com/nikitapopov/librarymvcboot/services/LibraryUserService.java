package com.nikitapopov.librarymvcboot.services;

import com.nikitapopov.librarymvcboot.models.User;
import com.nikitapopov.librarymvcboot.repositories.LibraryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LibraryUserService {

    private final LibraryUserRepository libraryUserRepository;

    @Autowired
    public LibraryUserService(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    public boolean isUserExisted(String username) {
        return libraryUserRepository.findByUsername(username).isPresent();
    }

    @Transactional
    public void save(User user) {
        libraryUserRepository.save(user);
    }
}
