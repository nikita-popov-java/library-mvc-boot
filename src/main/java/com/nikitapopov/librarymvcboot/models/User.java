package com.nikitapopov.librarymvcboot.models;

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
    private int passwordHash;
}
