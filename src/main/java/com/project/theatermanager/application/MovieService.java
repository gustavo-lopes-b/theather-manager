package com.project.theatermanager.application;

import com.project.theatermanager.acl.dto.MovieDTO;
import com.project.theatermanager.domain.Movie;
import com.project.theatermanager.infrastructure.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository repository;

    public List<MovieDTO> getAll() {
        return repository.findAll().stream().map(MovieDTO::fromEntity).collect(Collectors.toList());
    }

    public MovieDTO find(Long id) {
        return MovieDTO.fromEntity(this.findById(id));
    }

    protected Movie findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new InvalidConfigurationPropertyValueException("Movie", id, "Not found"));
    }

    public MovieDTO create(MovieDTO movieDTO) {
        return MovieDTO.fromEntity(repository.save(Movie.fromDTO(movieDTO)));
    }

    public MovieDTO update(Long id, MovieDTO movieDTO) {
        Movie oldMovie = this.findById(id);
        Movie newMovie = Movie.fromDTO(movieDTO);
        oldMovie.update(newMovie);
        return MovieDTO.fromEntity(repository.save(oldMovie));
    }
}
