package cinema.shows.repos;

import cinema.shows.entities.MoviePlaying;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface MoviePlayingRepo extends JpaRepository<MoviePlaying, Integer> {
    List<MoviePlaying> getAllByDateEndsIsGreaterThanEqualAndDateStartsIsLessThanEqual(Date dateOne, Date dateTwo);
    List<MoviePlaying> getAllByTheaterId(Integer theaterId);
    MoviePlaying findMoviePlayingByMovieIdAndTheaterId(int movieId, int theaterId);
}
