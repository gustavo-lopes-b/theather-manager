package com.project.theatermanager.infrastructure.repository;

import com.project.theatermanager.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("from Session s where s.theater.id = ?1 and s.movie.id = ?2")
    Set<Session> findByTheaterIdAndMovieId(Long theaterId, Long movieId);
}
