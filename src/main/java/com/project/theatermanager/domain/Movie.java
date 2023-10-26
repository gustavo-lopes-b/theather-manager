package com.project.theatermanager.domain;

import com.project.theatermanager.acl.dto.MovieDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Entity
public class Movie {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Boolean isActive;

    Movie(String name, String description) {
        this.name = name;
        this.description = description;
        this.isActive = true;
    }

    public static Movie fromDTO(MovieDTO dto){
        return new Movie(dto.name(), dto.description());
    }


    public void update(Movie newMovie) {
        this.name = newMovie.name;
        this.description = newMovie.description;
    }
}