package com.lms.libmanage.service;

import com.lms.libmanage.entity.Author;
import com.lms.libmanage.exception.NotFoundException;
import com.lms.libmanage.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Integer id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found with ID: " + id));
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Integer id, Author authorDetails) {
        Author author = getAuthorById(id);
        author.setFirstName(authorDetails.getFirstName());
        author.setLastName(authorDetails.getLastName());
        //author.setBiography(authorDetails.getBiography());
        //author.setBirthDate(authorDetails.getBirthDate());
        //author.setDeathDate(authorDetails.getDeathDate());
        return authorRepository.save(author);
    }

    public void deleteAuthor(Integer id) {
        Author author = getAuthorById(id);
        authorRepository.delete(author);
    }
}
