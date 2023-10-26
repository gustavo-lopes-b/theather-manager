package com.project.theatermanager.acl.dto;

import com.project.theatermanager.domain.Session;

import java.util.List;
import java.util.stream.Collectors;

public record SessionDTO(Long id, List<SeatDTO> seats){
    public static SessionDTO fromEntity(Session session){
        return new SessionDTO(session.getId(), session.getSeats().stream().map(SeatDTO::fromEntity).collect(Collectors.toList()));
    }
}
