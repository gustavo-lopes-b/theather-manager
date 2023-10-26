package com.project.theatermanager.application;

import com.project.theatermanager.acl.dto.SeatDTO;
import com.project.theatermanager.acl.dto.SessionDTO;
import com.project.theatermanager.acl.dto.TheaterDTO;
import com.project.theatermanager.domain.Movie;
import com.project.theatermanager.domain.Seat;
import com.project.theatermanager.domain.Session;
import com.project.theatermanager.domain.Theater;
import com.project.theatermanager.infrastructure.repository.SeatRepository;
import com.project.theatermanager.infrastructure.repository.SessionRepository;
import com.project.theatermanager.infrastructure.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository repository;
    private final SessionRepository sessionRepository;
    private final SeatRepository seatRepository;
    private final MovieService movieService;

    public List<TheaterDTO> getAll() {
        return repository.findAll().stream().map(TheaterDTO::fromEntity).collect(Collectors.toList());
    }

    private Theater findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new InvalidConfigurationPropertyValueException("Theater", id, "Not found"));
    }

    public TheaterDTO find(Long id) {
        return TheaterDTO.fromEntity(this.findById(id));
    }

    public List<TheaterDTO> getFromMovie(final Long movieId) {
        return repository.findAllByMovieId(movieId).stream().map(theater -> TheaterDTO.fromEntity(theater, movieId)).collect(Collectors.toList());
    }

    public TheaterDTO create(TheaterDTO theaterDTO) {
        return TheaterDTO.fromEntity(repository.save(Theater.fromDTO(theaterDTO)));
    }

    public TheaterDTO update(Long id, TheaterDTO theaterDTO) {
        Theater oldTheater = this.findById(id);
        Theater newTheater = Theater.fromDTO(theaterDTO);
        oldTheater.update(newTheater);
        return TheaterDTO.fromEntity(repository.save(oldTheater));
    }

    public SessionDTO addSession(Long movieId, Long theaterId){
        Theater theater = this.findById(theaterId);
        Movie movie = movieService.findById(movieId);
        Session session = new Session(movie, theater);
        return SessionDTO.fromEntity(sessionRepository.save(session));
    }

    @Transactional
    public TheaterDTO reserveSeat(Long sessionId, Long seatId, SeatDTO seatDTO) {
        Seat oldSeat = seatRepository.findSeatBySessionIdAndSeatId(sessionId, seatId).orElseThrow(() -> new InvalidConfigurationPropertyValueException("Seat", seatId, "Not found"));
        Seat newSeat = Seat.fromDTO(seatDTO);
        if(!oldSeat.getUserData().equals("empty")){
            throw new InvalidConfigurationPropertyValueException("Seat is already taken", oldSeat.getUserData(), "Invalid request");
        }
        if(!oldSeat.getCode().equals(seatDTO.code())){
            throw new InvalidConfigurationPropertyValueException("Seat id is not matching with the code", oldSeat.getCode(), "Invalid request");
        }
        oldSeat.update(newSeat);
        seatRepository.save(oldSeat);
        return TheaterDTO.fromEntity(repository.findBySessionId(sessionId)
                .orElseThrow(() -> new InvalidConfigurationPropertyValueException("Session for Theater", sessionId, "Not found")));
    }

    public Set<SessionDTO> retrieveSessions(Long movieId, Long theaterId){
        return sessionRepository.findByTheaterIdAndMovieId(theaterId, movieId).stream().map(SessionDTO::fromEntity).collect(Collectors.toSet());
    }
}
