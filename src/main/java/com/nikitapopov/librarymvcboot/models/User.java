package com.nikitapopov.librarymvcboot.models;

import com.nikitapopov.librarymvcboot.models.enums.Authority;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "library_user")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    @NonNull
    private String username;

    @Column(name = "password")
    @NonNull
    private String password;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    @NonNull
    private Authority authority;
}
