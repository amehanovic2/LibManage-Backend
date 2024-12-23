package com.lms.libmanage.service;

import com.lms.libmanage.entity.Genre;
import com.lms.libmanage.exception.NotFoundException;
import com.lms.libmanage.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(Integer id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre not found with ID: " + id));
    }

    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre updateGenre(Integer id, Genre genreDetails) {
        Genre genre = getGenreById(id);
        genre.setGenreName(genreDetails.getGenreName());
        //genre.setGenreDescription(genreDetails.getGenreDescription());
        return genreRepository.save(genre);
    }

    public void deleteGenre(Integer id) {
        Genre genre = getGenreById(id);
        genreRepository.delete(genre);
    }
}
