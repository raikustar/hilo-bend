package com.rainer.highlow.repository;

import com.rainer.highlow.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    List<Score> findByOrderByScoreAsc();
    List<Score> findByOrderByScoreDesc();


    List<Score> findByOrderByTotalTimeAsc();
    List<Score> findByOrderByTotalTimeDesc();

    List<Score> findByPlayer_Name(String name);

}
