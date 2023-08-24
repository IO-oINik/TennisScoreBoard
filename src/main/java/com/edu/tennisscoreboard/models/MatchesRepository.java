package com.edu.tennisscoreboard.models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MatchesRepository extends CrudRepository<Match, Long> {
    @Query(value = "select m from Match m where m.player1=?1 or m.player2=?1 or m.winner=?1")
    Iterable<Match> findAllByPlayer(Player player);
}
