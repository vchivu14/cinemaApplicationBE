package cinema.shows.repos;

import cinema.shows.entities.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallRepo extends JpaRepository<Hall, Integer> {
}
