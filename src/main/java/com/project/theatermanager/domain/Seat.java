package com.project.theatermanager.domain;

import com.project.theatermanager.acl.dto.SeatDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Seat {

    @Id
    @GeneratedValue
    private Long id;
    private String userData;
    private String code;

    @ManyToOne
    @JoinColumn(name="movie_session_id", insertable = false, updatable = false)
    private Session session;

    public Seat(String code){
        this.userData = "empty";
        this.code = code;
    }


    public static Seat fromDTO(SeatDTO seatDTO) {
        Seat seat = new Seat(seatDTO.code());
        seat.userData = seatDTO.user();
        return seat;
    }

    public void update(Seat newSeat) {
        this.userData = newSeat.userData;
    }
}