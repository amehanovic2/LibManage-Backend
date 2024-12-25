package com.lms.libmanage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    Integer bookId;

    @NotBlank(message = "Book name cannot be empty")
    @Size(max = 255, message = "Book name cannot exceed 255 characters")
    @Column(name = "book_name", nullable = false)
    String bookName;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    Author bookAuthor;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    Genre bookGenre;

    @NotNull(message = "Number of copies cannot be null")
    @Column(name = "no_of_copies", nullable = false)
    Integer noOfCopies;

    public Book(String bookName, Author bookAuthor, Genre bookGenre, Integer noOfCopies) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookGenre = bookGenre;
        this.noOfCopies = noOfCopies;
    }
}
