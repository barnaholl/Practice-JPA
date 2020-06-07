package com.codecool.practicejpa.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Episode {

    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false, unique = true)
    private String title;
    private LocalDate releaseDate;
    @Transient
    private int age;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Season season;

    public void calculateAge(){
        if(releaseDate !=null){
            age= (int) ChronoUnit.YEARS.between(releaseDate,LocalDate.now());
        }
    }

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Singular
    private List<Genre> genres;



}
