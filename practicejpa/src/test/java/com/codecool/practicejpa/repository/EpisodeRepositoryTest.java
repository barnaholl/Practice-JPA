package com.codecool.practicejpa.repository;

import com.codecool.practicejpa.entity.Episode;
import com.codecool.practicejpa.entity.Season;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
class EpisodeRepositoryTest {
    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void saveOneSimple(){
        Episode episode1 = Episode.builder().title("episode1").build();
        episodeRepository.save(episode1);

        List<Episode> episodeList=episodeRepository.findAll();
        assertThat(episodeList).hasSize(1);
    }

    @Test
    public void calculateAge(){
        Episode episode1= Episode.builder().title("episode1").releaseDate(LocalDate.of(1996,9,9)).build();
        episode1.calculateAge();
        assertThat(episode1.getAge()).isGreaterThanOrEqualTo(23);

    }

    @Test
    public void SeasonPersistedWithEpisode(){
        Episode episode1 = Episode.builder().title("episode1").build();
        Episode episode2 = Episode.builder().title("episode2").build();
        episodeRepository.save(episode1);
        episodeRepository.save(episode2);
        Season season1 = Season.builder().title("season1").episodes(episodeRepository.findAll()).build();
        seasonRepository.save(season1);

        assertThat(seasonRepository.findAll()).hasSize(1).allMatch(season->season1.getEpisodes().size()==2);

    }
    @Test
    public void findByTitleStartsWithAndReleaseDateIsAfterTest(){
        Episode episode1 = Episode.builder()
                .title("episode1")
                .releaseDate(LocalDate.of(1996,9,9))
                .build();

        Episode episode2 = Episode.builder()
                .title("episode2")
                .releaseDate(LocalDate.of(2000,1,1))
                .build();

        Episode episode3 = Episode.builder()
                .title("notepisode3")
                .releaseDate(LocalDate.of(2000,1,1))
                .build();


        episodeRepository.saveAll(Lists.newArrayList(episode1,episode2,episode3));

        List<Episode> resultList=episodeRepository.findByTitleStartsWithAndReleaseDateIsAfter("e", LocalDate.of(1999, 1, 1));

        assertThat(resultList).containsExactlyInAnyOrder(episode2);
    }

    @Test
    public void findAllBySeasonEqualsTest(){
        Episode episode1 = Episode.builder()
                .title("Episode1")
                .releaseDate(LocalDate.of(1996,9,9))
                .build();
        Episode episode2 = Episode.builder()
                .title("Episode2")
                .releaseDate(LocalDate.of(1996,9,16))
                .build();
        Episode episode3 = Episode.builder()
                .title("Episode3")
                .releaseDate(LocalDate.of(1996,9,23))
                .build();
        Episode episode4 = Episode.builder()
                .releaseDate(LocalDate.of(2000,1,6))
                .title("Episode4")
                .build();
        Episode episode5 = Episode.builder()
                .title("Episode5")
                .releaseDate(LocalDate.of(2000,9,20))
                .build();
        Episode episode6 = Episode.builder()
                .title("Episode6")
                .releaseDate(LocalDate.of(2016,9,9))
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

        seasonRepository.saveAll(Lists.newArrayList(season1,season2,season3));
        List<Episode> resultList = episodeRepository.findAllBySeasonEquals(season3);

        assertThat(resultList).containsExactlyInAnyOrder(episode6);

    }

    @Test()
    public void saveUniqueFieldTwice(){
        Episode episode1=Episode.builder().title("episode1").build();
        Episode episode2=Episode.builder().title("episode1").build();
        episodeRepository.save(episode1);
        assertThrows(DataIntegrityViolationException.class,()->episodeRepository.saveAndFlush(episode2));

    }
}