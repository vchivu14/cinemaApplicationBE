package cinema.shows.rest;

import cinema.shows.dtos.InputMovieDTO;
import cinema.shows.dtos.MovieDTOFull;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MovieRESTAPI {
    ResponseEntity<List<MovieDTOFull>> getAllMovies();
    ResponseEntity<MovieDTOFull> addMovie(InputMovieDTO inputMovieDTO);
    ResponseEntity<MovieDTOFull> getMovie(int id);
    ResponseEntity<MovieDTOFull> updateMovie(MovieDTOFull movieDTOFull, Boolean replace);
    void removeMovie(int id);
}
