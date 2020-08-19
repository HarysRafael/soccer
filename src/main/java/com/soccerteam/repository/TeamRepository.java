package com.soccerteam.repository;

import com.soccerteam.model.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TeamRepository extends MongoRepository<Team, String> {

    Optional<Team> findByNameAndActivateTrue(String name);
    Optional<Team> findByIdAndActivateTrue(String id);
    Optional<Team> findByPlayersId(String playerId);

}
