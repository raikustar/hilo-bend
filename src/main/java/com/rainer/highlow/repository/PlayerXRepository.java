package com.rainer.highlow.repository;

import com.rainer.highlow.entity.PlayerComplex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerXRepository extends JpaRepository<PlayerComplex,Long > {
    List<PlayerComplex> findAllByOrderByScore_ScoreAscNameAsc();
}
