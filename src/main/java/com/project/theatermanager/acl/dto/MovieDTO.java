package com.project.theatermanager.acl.dto;

import com.project.theatermanager.domain.Movie;

public record MovieDTO(Long id, String name, String description){
    public static MovieDTO fromEntity(Movie movie){
        return new MovieDTO(movie.getId(), movie.getName(), movie.getDescription());
    }
}
