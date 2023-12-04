package com.nikitapopov.librarymvcboot.repositories;

import com.nikitapopov.librarymvcboot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
