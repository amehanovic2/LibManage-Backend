package com.lms.libmanage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
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

    @NotBlank(message = "Author name cannot be empty")
    @Size(max = 255, message = "Author name cannot exceed 255 characters")
    @Column(name = "book_author", nullable = false)
    String bookAuthor;

    @NotBlank(message = "Genre cannot be empty")
    @Size(max = 100, message = "Genre cannot exceed 100 characters")
    @Column(name = "book_genre", nullable = false)
    String bookGenre;

    @NotNull(message = "Number of copies cannot be null")
    @Column(name = "no_of_copies", nullable = false)
    Integer noOfCopies;
}
