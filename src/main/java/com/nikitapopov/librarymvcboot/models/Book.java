package com.nikitapopov.librarymvcboot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"holder"})
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Название книги не может быть пустым!")
    @NonNull
    private String name;

    @Column(name = "author")
    @NotBlank(message = "У книги не может не быть автора!")
    @NonNull
    private String author;

    @Column(name = "year_of_created")
    @Max(value = 2023, message = "Книга не может быть написана позже 2023 года! (на момент разработки приложения)")
    @NonNull
    private int yearOfCreated;

    @Column(name = "receipt_date")
    @Temporal(TemporalType.TIMESTAMP)
    @NonNull
    private Date receiptDate;

    @ManyToOne
    @JoinColumn(name = "holder_id", referencedColumnName = "id")
    private Person holder;

    @Transient
    @Getter
    @Setter
    private boolean isOverdue;

    public boolean isFree() {
        return this.holder == null;
    }
}