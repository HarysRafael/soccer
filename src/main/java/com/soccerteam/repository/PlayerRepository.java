package com.soccerteam.repository;

import com.soccerteam.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {

    Optional<Player> findByNameAndActivateTrue(String name);
    Optional<Player> findByIdAndActivateTrue(String id);

}
