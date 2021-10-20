package cinema.shows.repos;

import cinema.shows.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepo extends JpaRepository<Actor, Integer> {
    Actor findActorByFirstNameAndLastName(String firstName, String lastName);
}
