package com.nikitapopov.librarymvcboot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor()
@ToString(exclude = {"books"})
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    @NotBlank(message = "Имя пользователя не может быть пустым!")
    @Pattern(message = "Введите данные в виде 'Фамилия Имя Отчество'",regexp = "([а-яА-Яё]*? ){2}([а-яА-Яё]*?)")
    @Size(min = 5, max = 50, message = "Недопустимая длина ФИО")
    @NonNull
    private String fullName;

    @Column(name = "year_of_birth")
    @Max(value = 2023, message = "Человек не может быть рождён позже 2023 года! (на момент разработки приложения)")
    @NonNull
    private int yearOfBirth;

    @OneToMany(mappedBy = "holder", cascade = CascadeType.PERSIST)
    private List<Book> books;

    public List<Book> getBooks() {
        if (books == null)
            books = new ArrayList<>();
        return books;
    }
}