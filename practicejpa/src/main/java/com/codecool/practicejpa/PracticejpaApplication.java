package com.codecool.practicejpa;

import com.codecool.practicejpa.entity.Episode;
import com.codecool.practicejpa.entity.Genre;
import com.codecool.practicejpa.entity.Season;
import com.codecool.practicejpa.entity.Series;
import com.codecool.practicejpa.repository.EpisodeRepository;
import com.codecool.practicejpa.repository.SeasonRepository;
import com.codecool.practicejpa.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PracticejpaApplication {

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    public static void main(String[] args) {
        SpringApplication.run(PracticejpaApplication.class, args);
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init(){
        return args -> {
            Episode episode1 = Episode.builder()
                    .title("Episode1")
                    .releaseDate(LocalDate.of(1996,9,9))
                    .genre(Genre.HORROR)
                    .genre(Genre.COMEDY)
                    .build();
            Episode episode2 = Episode.builder()
                    .title("Episode2")
                    .releaseDate(LocalDate.of(1996,9,16))
                    .genre(Genre.HORROR)
                    .genre(Genre.THRILLER)
                    .build();
            Episode episode3 = Episode.builder()
                    .title("Episode3")
                    .releaseDate(LocalDate.of(1996,9,23))
                    .genre(Genre.HORROR)
                    .build();
            Episode episode4 = Episode.builder()
                    .releaseDate(LocalDate.of(2000,1,6))
                    .genre(Genre.HORROR)
                    .genre(Genre.THRILLER)
                    .title("Episode4")
                    .build();
            Episode episode5 = Episode.builder()
                    .title("Episode5")
                    .releaseDate(LocalDate.of(2000,9,20))
                    .genre(Genre.HORROR)
                    .genre(Genre.THRILLER)
                    .build();
            Episode episode6 = Episode.builder()
                    .title("Episode6")
                    .releaseDate(LocalDate.of(2016,9,9))
                    .genre(Genre.THRILLER)
                    .build();

            Season season1=Season.builder()
                    .title("Season1")
                    .episode(episode1)
                    .episode(episode2)
                    .episode(episode3)
                    .build();

            Season season2=Season.builder()
                    .title("Season2")
                    .episode(episode4)
                    .episode(episode5)
                    .build();

            Season season3=Season.builder()
                    .title("Season3")
                    .episode(episode6)
                    .build();

            episode1.setSeason(season1);
            episode2.setSeason(season1);
            episode3.setSeason(season1);
            episode4.setSeason(season2);
            episode5.setSeason(season2);
            episode6.setSeason(season3);

            Series series1=Series.builder()
                    .title("Average series")
                    .season(season1)
                    .season(season2)
                    .season(season3)
                    .build();

            season1.setSeries(series1);
            season2.setSeries(series1);
            season3.setSeries(series1);

            seriesRepository.save(series1);
        };
    }

}
