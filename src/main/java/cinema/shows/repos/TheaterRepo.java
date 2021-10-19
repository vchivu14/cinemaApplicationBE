package cinema.shows.repos;

import cinema.shows.entities.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepo extends JpaRepository<Theater, Integer> {
}
