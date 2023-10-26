package com.project.theatermanager.acl.dto;

import com.project.theatermanager.domain.Seat;

public record SeatDTO(Long id, String user, String code){
    public static SeatDTO fromEntity(Seat seat){
        return new SeatDTO(seat.getId(), seat.getUserData(), seat.getCode());
    }
}
