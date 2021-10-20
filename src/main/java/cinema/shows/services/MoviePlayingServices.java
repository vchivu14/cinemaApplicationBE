package cinema.shows.services;

import cinema.shows.dtos.EditMoviePlayingDTO;
import cinema.shows.dtos.InputMoviePlayingDTO;
import cinema.shows.dtos.MoviePlayingDTOFull;
import cinema.shows.dtos.MoviePlayingDTOMin;
import cinema.shows.entities.MoviePlaying;

import java.sql.Date;
import java.util.List;

public interface MoviePlayingServices {
    MoviePlaying getMoviePlaying(Integer moviePlayingId);
    MoviePlaying getMoviePlayingByMovieAndTheater(Integer movieId, Integer theaterId);

    MoviePlayingDTOMin addMoviePlayingInTheater(InputMoviePlayingDTO inputMoviePlayingDTO);
    MoviePlayingDTOMin updateMoviePlayingInTheater(InputMoviePlayingDTO inputMoviePlayingDTO, int moviePlayingId);
    MoviePlayingDTOMin updateMoviePlayingInTheater(EditMoviePlayingDTO editMoviePlayingDTO);
    void removeMoviePlayingInTheater(Integer moviePlayingId);

    MoviePlayingDTOFull getMoviePlayingInTheater(Integer moviePlayingId);
    List<MoviePlayingDTOMin> getAllMinMoviesPlayingInTheater(Integer theaterId);
    List<MoviePlayingDTOMin> getAllMinMoviesPlayingForDate(Date date);
    List<MoviePlayingDTOMin> getAllMinMoviesPlayingForDates(Date dateStarts, Date dateEnds);

    MoviePlayingDTOMin getMinMoviePlayingInTheater(Integer moviePlayingId);
    List<MoviePlayingDTOFull> getAllMoviesPlayingInTheater(Integer theaterId);
    List<MoviePlayingDTOFull> getAllMoviesPlayingForDate(Date date);
    List<MoviePlayingDTOFull> getAllMoviesPlayingForDates(Date dateStarts, Date dateEnds);
}
