package com.codecool.practicejpa.repository;

import com.codecool.practicejpa.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeriesRepository extends JpaRepository<Series,Integer> {

     List<Series> getAllByIdBetween(int f, int l);
}
