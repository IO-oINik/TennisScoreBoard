package com.edu.tennisscoreboard.models;

import org.springframework.data.repository.CrudRepository;

public interface PlayersRepository extends CrudRepository<Player, Long> {
    Player findByNameIgnoreCase(String name);
}
