package cinema.shows.rest;

import cinema.shows.dtos.EditMoviePlayingDTO;
import cinema.shows.dtos.InputMoviePlayingDTO;
import cinema.shows.dtos.MoviePlayingDTOFull;
import cinema.shows.dtos.MoviePlayingDTOMin;
import io.swagger.models.auth.In;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MoviePlayingRESTAPI {
    ResponseEntity<List<MoviePlayingDTOFull>> getAllMoviesPlayingForTheater(int theaterId);
    ResponseEntity<MoviePlayingDTOMin> addMoviePlayingForTheater(InputMoviePlayingDTO inputMoviePlayingDTO);
    void removeMoviePlaying(int id);
    ResponseEntity<MoviePlayingDTOMin> editMoviePlayingForTheater(InputMoviePlayingDTO inputMoviePlayingDTO, int moviePlayingId);
    ResponseEntity<MoviePlayingDTOMin> editMoviePlayingForTheater(EditMoviePlayingDTO editMoviePlayingDTO);
    ResponseEntity<List<MoviePlayingDTOFull>> getAllMoviesPlayingForDate(String date);
    ResponseEntity<List<MoviePlayingDTOFull>> getAllMoviesPlayingForDates(String dateStarts, String dateEnds);
}
