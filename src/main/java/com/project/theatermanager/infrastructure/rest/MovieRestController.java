package com.project.theatermanager.infrastructure.rest;

import com.project.theatermanager.acl.dto.MovieDTO;
import com.project.theatermanager.application.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movie")
@RequiredArgsConstructor
class MovieRestController {

    private final MovieService service;

    @GetMapping
    List<MovieDTO> retrieveAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    MovieDTO retrieveOne(@PathVariable Long id) {
        return service.find(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    MovieDTO createMovie(@RequestBody MovieDTO movieDTO) {
        return service.create(movieDTO);
    }

    @PutMapping("/{id}")
    MovieDTO updateMovie(@RequestBody MovieDTO movieDTO, @PathVariable Long id) {
        return service.update(id, movieDTO);
    }

}