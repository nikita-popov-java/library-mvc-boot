package com.nikitapopov.librarymvcboot.security;

import com.nikitapopov.librarymvcboot.models.User;
import com.nikitapopov.librarymvcboot.services.LibraryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AuthenticationProviderImplementation implements AuthenticationProvider {

    private final LibraryUserDetailsService libraryUserDetailsService;

    @Autowired
    public AuthenticationProviderImplementation(LibraryUserDetailsService libraryUserDetailsService) {
        this.libraryUserDetailsService = libraryUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        int passwordHash = authentication.getCredentials().toString().hashCode();
        LibraryUserDetails userDetails = (LibraryUserDetails) libraryUserDetailsService.loadUserByUsername(username);
        int correctPasswordHash = Integer.parseInt(userDetails.getPassword());

        if (passwordHash != correctPasswordHash)
            throw new BadCredentialsException("Неправильный пароль!");

        return new UsernamePasswordAuthenticationToken(userDetails.getUser(), correctPasswordHash, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
