package com.project.theatermanager.infrastructure.repository;

import com.project.theatermanager.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
