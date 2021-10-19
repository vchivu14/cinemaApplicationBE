package cinema.shows.rest;

import cinema.shows.dtos.InputMoviePlayingDTO;
import cinema.shows.dtos.MoviePlayingDTOFull;
import cinema.shows.dtos.MoviePlayingDTOMin;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MoviePlayingRESTAPI {
    ResponseEntity<List<MoviePlayingDTOFull>> getAllMoviesPlayingForTheater(int theaterId);
    ResponseEntity<MoviePlayingDTOMin> addMoviePlayingForTheater(InputMoviePlayingDTO inputMoviePlayingDTO);
    ResponseEntity<List<MoviePlayingDTOFull>> getAllMoviesPlayingForDate(String date);
    ResponseEntity<List<MoviePlayingDTOFull>> getAllMoviesPlayingForDates(String dateStarts, String dateEnds);
}
