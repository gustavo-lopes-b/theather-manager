package com.project.theatermanager.acl.dto;

import com.project.theatermanager.domain.Theater;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record TheaterDTO(Long id, String name, List<SessionDTO> sessions){
    public static TheaterDTO fromEntity(Theater theater, Long movieId){
        return new TheaterDTO(theater.getId(), theater.getName(),
                theater.getSessions().stream().filter(session -> session.getMovieId().equals(movieId))
                        .map(SessionDTO::fromEntity).collect(Collectors.toList()));
    }

    public static TheaterDTO fromEntity(Theater theater){
        return new TheaterDTO(theater.getId(), theater.getName(), CollectionUtils.isEmpty(theater.getSessions()) ? Collections.emptyList():
                theater.getSessions().stream().map(SessionDTO::fromEntity).collect(Collectors.toList()));
    }
}
