package com.codecool.practicejpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Series {
    @Id
    @GeneratedValue
    private  int id;
    private String title;

    @Singular
    @OneToMany(mappedBy = "series",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @EqualsAndHashCode.Exclude
    private List<Season> seasons;
}
