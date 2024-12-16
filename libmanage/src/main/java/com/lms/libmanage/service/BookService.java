package com.lms.libmanage.service;

import com.lms.libmanage.entity.Book;
import com.lms.libmanage.exception.NotFoundException;
import com.lms.libmanage.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Integer id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + id));
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Integer id, Book bookDetails) {
        Book book = getBookById(id);
        book.setBookName(bookDetails.getBookName());
        book.setBookAuthor(bookDetails.getBookAuthor());
        book.setBookGenre(bookDetails.getBookGenre());
        book.setNoOfCopies(bookDetails.getNoOfCopies());
        return bookRepository.save(book);
    }

    public void deleteBook(Integer id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }
}
