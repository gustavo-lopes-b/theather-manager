package com.project.theatermanager.infrastructure.repository;

import com.project.theatermanager.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query("select se from Session s inner join s.seats se where s.id = ?1 and se.id = ?2")
    Optional<Seat> findSeatBySessionIdAndSeatId(Long sessionId, Long seatId);
}
