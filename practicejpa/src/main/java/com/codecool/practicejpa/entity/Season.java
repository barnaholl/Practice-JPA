package com.codecool.practicejpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Season {
    @Id
    @GeneratedValue
    private int id;
    private String title;

    @Singular
    @OneToMany(mappedBy = "season",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @EqualsAndHashCode.Exclude
    private List<Episode> episodes;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Series series;

}
