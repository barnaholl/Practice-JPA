package com.codecool.practicejpa.repository;

import com.codecool.practicejpa.entity.Episode;
import com.codecool.practicejpa.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode,Long> {

    List<Episode> findByTitleStartsWithAndReleaseDateIsAfter(String title, LocalDate from);

    List<Episode> findAllBySeasonEquals(Season season);

}
