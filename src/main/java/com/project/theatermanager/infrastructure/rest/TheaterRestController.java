package com.project.theatermanager.infrastructure.rest;

import com.project.theatermanager.acl.dto.SeatDTO;
import com.project.theatermanager.acl.dto.SessionDTO;
import com.project.theatermanager.acl.dto.TheaterDTO;
import com.project.theatermanager.application.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("theater")
@RequiredArgsConstructor
class TheaterRestController {

    private final TheaterService service;

    @GetMapping
    List<TheaterDTO> retrieveAll() {
        return service.getAll();
    }

    @GetMapping("/by-movie/{movieId}")
    List<TheaterDTO> retrieveFromMovie(@PathVariable Long movieId) {
        return service.getFromMovie(movieId);
    }

    @GetMapping("/{id}")
    TheaterDTO retrieveOne(@PathVariable Long id) {
        return service.find(id);
    }

    @GetMapping("/{id}/retrieve-sessions/{movieId}")
    Set<SessionDTO> retrieveSessions(@PathVariable Long id, @PathVariable Long movieId) {
        return service.retrieveSessions(id, movieId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TheaterDTO createTheater(@RequestBody TheaterDTO theaterDTO) {
        return service.create(theaterDTO);
    }

    @PostMapping("/{id}/create-session/{movieId}")
    @ResponseStatus(HttpStatus.CREATED)
    SessionDTO newSession(@PathVariable Long id, @PathVariable Long movieId) {
        return service.addSession(movieId, id);
    }

    @PutMapping("/{id}")
    TheaterDTO replaceTheater(@RequestBody TheaterDTO theaterDTO, @PathVariable Long id) {
        return service.update(id, theaterDTO);
    }

    @PutMapping("{theaterId}/{sessionId}/reserve-seat/{id}")
    TheaterDTO reserveSeat(@RequestBody SeatDTO seatDTO, @PathVariable Long theaterId, @PathVariable Long sessionId, @PathVariable Long id) {
        return service.reserveSeat(sessionId, id,  seatDTO);
    }

}