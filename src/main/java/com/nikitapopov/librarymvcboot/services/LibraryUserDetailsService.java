package com.nikitapopov.librarymvcboot.services;

import com.nikitapopov.librarymvcboot.models.User;
import com.nikitapopov.librarymvcboot.repositories.LibraryUserRepository;
import com.nikitapopov.librarymvcboot.security.LibraryUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibraryUserDetailsService implements UserDetailsService {
    private final LibraryUserRepository libraryUserRepository;

    @Autowired
    public LibraryUserDetailsService(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = libraryUserRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Такого пользователя не существует!");
        }

        return new LibraryUserDetails(user.get());
    }
}
