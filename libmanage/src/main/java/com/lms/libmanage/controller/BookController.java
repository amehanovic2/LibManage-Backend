package com.lms.libmanage.controller;

import com.lms.libmanage.entity.Author;
import com.lms.libmanage.entity.Book;
import com.lms.libmanage.entity.Genre;
import com.lms.libmanage.exception.NotFoundException;
import com.lms.libmanage.service.AuthorService;
import com.lms.libmanage.service.BookService;
import com.lms.libmanage.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    //@PreAuthorize("hasRole('LIBRARY_STAFF') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Author author = authorService.getAuthorById(book.getBookAuthor().getAuthorId());
        Genre genre = genreService.getGenreById(book.getBookGenre().getGenreId());

        book.setBookAuthor(author);
        book.setBookGenre(genre);

        Book newBook = bookService.createBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer id, @Valid  @RequestBody Book bookDetails) {
        Author author = authorService.getAuthorById(bookDetails.getBookAuthor().getAuthorId());
        Genre genre = genreService.getGenreById(bookDetails.getBookGenre().getGenreId());

        bookDetails.setBookAuthor(author);
        bookDetails.setBookGenre(genre);

        Book updatedBook = bookService.updateBook(id, bookDetails);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
