package com.project.theatermanager.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "MOVIE_SESSION")
public class Session {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_session_id")
    @OrderBy("code")
    private Set<Seat> seats;

    public Long getMovieId(){
        return movie.getId();
    }

    public Session(Movie movie, Theater theater){
        this.movie = movie;
        this.theater = theater;
        this.seats = new HashSet<>();
        for(int i = 0; i<20; i++){
            String letter = "";
            if(i < 5) letter = "a";
            if(i >=5 && i < 10) letter = "b";
            if(i >=10 && i < 15) letter = "c";
            if(i >=15) letter = "d";
            Seat seat = new Seat(letter+((i%5)+1));
            this.seats.add(seat);
        }
    }



}