package com.project.theatermanager.infrastructure.repository;

import com.project.theatermanager.domain.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface TheaterRepository extends JpaRepository<Theater, Long> {

    @Query("from Theater t inner join t.sessions s inner join s.movie m where m.id = ?1")
    Set<Theater> findAllByMovieId(Long movieId);

    @Query("select t from Session s inner join s.theater t where s.id = ?1")
    Optional<Theater> findBySessionId(Long sessionId);
}
