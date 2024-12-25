package com.lms.libmanage.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Entity
@Getter
@Setter
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "genre_id")
    private Integer genreId;

    @NotBlank(message = "Genre name cannot be empty")
    @Size(max = 255, message = "Genre name cannot exceed 255 characters")
    @Column(name = "genre_name", nullable = false)
    private String genreName;

    /*@OneToMany(mappedBy = "bookGenre")
    private List<Book> books;*/

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public Genre() {
    }
}
