package com.rainer.highlow.repository;

import com.rainer.highlow.entity.Player;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findAllByOrderByScore_ScoreAscNameAsc();
}
