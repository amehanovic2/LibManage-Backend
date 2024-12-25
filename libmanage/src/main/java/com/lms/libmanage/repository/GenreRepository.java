package com.lms.libmanage.repository;

import com.lms.libmanage.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Genre findByGenreName(String genreName);
}
