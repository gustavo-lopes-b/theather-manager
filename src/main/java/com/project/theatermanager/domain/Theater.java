package com.project.theatermanager.domain;

import com.project.theatermanager.acl.dto.TheaterDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
public class Theater {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany
    @JoinColumn(name = "theater_id", insertable = false, updatable = false)
    private Set<Session> sessions;

    Theater(String name) {
        this.name = name;
    }


    public static Theater fromDTO(TheaterDTO theaterDTO) {
        return new Theater(theaterDTO.name());
    }

    public void update(Theater newTheater) {
        this.name = newTheater.name;
    }
}