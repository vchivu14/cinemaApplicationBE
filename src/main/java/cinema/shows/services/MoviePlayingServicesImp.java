package cinema.shows.services;

import cinema.shows.dtos.*;
import cinema.shows.entities.Movie;
import cinema.shows.entities.MoviePlaying;
import cinema.shows.entities.Theater;
import cinema.shows.exceptions.ResourceNotFoundException;
import cinema.shows.repos.MoviePlayingRepo;
import cinema.shows.repos.MovieRepo;
import cinema.shows.repos.TheaterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class MoviePlayingServicesImp implements MoviePlayingServices {
    private MoviePlayingRepo moviePlayingRepo;
    @Autowired
    MovieServices movieServices;
    @Autowired
    TheaterRepo theaterRepo;
    @Autowired
    MovieRepo movieRepo;

    public MoviePlayingServicesImp(MoviePlayingRepo moviePlayingRepo) {
        this.moviePlayingRepo = moviePlayingRepo;
    }

    private String errorMessage(Integer moviePlayingId){
        return "No Movie Playing found with Id = " + moviePlayingId;
    }


    public MoviePlaying getMoviePlayingFromInput(InputMoviePlayingDTO inputMoviePlayingDTO) {
        MoviePlaying moviePlaying = new MoviePlaying();
        moviePlaying.setDateStarts(Date.valueOf(inputMoviePlayingDTO.getDateStarts()));
        moviePlaying.setDateEnds(Date.valueOf(inputMoviePlayingDTO.getDateEnds()));
        Theater theater = theaterRepo.getById(inputMoviePlayingDTO.getTheaterId());
        moviePlaying.setTheater(theater);
        moviePlaying.setMovieId(inputMoviePlayingDTO.getMovieId());
        return moviePlaying;
    }

    @Override
    public MoviePlaying getMoviePlaying(Integer moviePlayingId) {
        return moviePlayingRepo.findById(moviePlayingId)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(moviePlayingId)));
    }

    @Override
    public MoviePlaying getMoviePlayingByMovieAndTheater(Integer movieId, Integer theaterId) {
        return moviePlayingRepo.findMoviePlayingByMovieIdAndTheaterId(movieId,theaterId);
    }

    private MoviePlayingDTOFull getMoviePlayingDTOFull(MoviePlaying moviePlaying) {
        MoviePlayingDTOFull moviePlayingDTOFull = new MoviePlayingDTOFull();
        Movie movie = movieServices.getMovieById(moviePlaying.getMovieId());
        MovieDTOFull movieDTOFull = movieServices.getMovieDTOFullFromMovie(movie);
        moviePlayingDTOFull.setMovieDTOFull(movieDTOFull);
        moviePlayingDTOFull.setDateStarts(moviePlaying.getDateStarts());
        moviePlayingDTOFull.setDateEnds(moviePlaying.getDateEnds());
        moviePlayingDTOFull.setTheater(moviePlaying.getTheater().getName());
        return moviePlayingDTOFull;
    }
    private List<MoviePlayingDTOFull> getMoviesPlayingDTOsFull(List<MoviePlaying> moviesPlaying) {
        List<MoviePlayingDTOFull> moviePlayingDTOsFull = new ArrayList<>();
        for (MoviePlaying m: moviesPlaying) {
            moviePlayingDTOsFull.add(getMoviePlayingDTOFull(m));
        }
        return moviePlayingDTOsFull;
    }
    private MoviePlayingDTOMin getMoviePlayingDTOMin(MoviePlaying moviePlaying) {
        MoviePlayingDTOMin moviePlayingDTOMin = new MoviePlayingDTOMin();
        Movie movie = movieServices.getMovieById(moviePlaying.getMovieId());
        MovieDTOMin movieDTOMin = movieServices.getMovieDTOMinFromMovie(movie);
        moviePlayingDTOMin.setMovieDTOMin(movieDTOMin);
        moviePlayingDTOMin.setDateStarts(moviePlaying.getDateStarts());
        moviePlayingDTOMin.setDateEnds(moviePlaying.getDateEnds());
        moviePlayingDTOMin.setTheater(moviePlaying.getTheater().getName());
        return moviePlayingDTOMin;
    }
    private List<MoviePlayingDTOMin> getMoviesPlayingDTOsMin(List<MoviePlaying> moviesPlaying) {
        List<MoviePlayingDTOMin> moviePlayingDTOsMin = new ArrayList<>();
        for (MoviePlaying m: moviesPlaying) {
            moviePlayingDTOsMin.add(getMoviePlayingDTOMin(m));
        }
        return moviePlayingDTOsMin;
    }
    @Override
    public MoviePlayingDTOMin addMoviePlayingInTheater(InputMoviePlayingDTO inputMoviePlayingDTO) {
        MoviePlaying moviePlaying = moviePlayingRepo.findMoviePlayingByMovieIdAndTheaterId
                (inputMoviePlayingDTO.getMovieId(), inputMoviePlayingDTO.getTheaterId());
        if (moviePlaying == null) {
            MoviePlaying newMoviePlaying = getMoviePlayingFromInput(inputMoviePlayingDTO);
            moviePlaying = moviePlayingRepo.save(newMoviePlaying);
        }
        return getMoviePlayingDTOMin(moviePlaying);
    }

    @Override
    public MoviePlayingDTOMin updateMoviePlayingInTheater(InputMoviePlayingDTO inputMoviePlayingDTO) {
        int movieId = inputMoviePlayingDTO.getMovieId();
        int theaterId = inputMoviePlayingDTO.getTheaterId();
        MoviePlaying moviePlayingInDB = getMoviePlayingByMovieAndTheater(movieId,theaterId);
        Date dateStarts = Date.valueOf(inputMoviePlayingDTO.getDateStarts());
        Date dateEnds = Date.valueOf(inputMoviePlayingDTO.getDateEnds());
        if (dateStarts != null) {
            moviePlayingInDB.setDateStarts(dateStarts);
        }
        if (dateEnds != null) {
            moviePlayingInDB.setDateEnds(dateEnds);
        }
        MoviePlaying moviePlayingSaved = moviePlayingRepo.save(moviePlayingInDB);
        return getMoviePlayingDTOMin(moviePlayingSaved);
    }

    @Override
    public void removeMoviePlayingInTheater(Integer moviePlayingId) {
        getMoviePlaying(moviePlayingId);
        moviePlayingRepo.deleteById(moviePlayingId);
    }

    @Override
    public MoviePlayingDTOFull getMoviePlayingInTheater(Integer moviePlayingId) {
        MoviePlaying moviePlaying = getMoviePlaying(moviePlayingId);
        return getMoviePlayingDTOFull(moviePlaying);
    }

    @Override
    public List<MoviePlayingDTOFull> getAllMoviesPlayingInTheater(Integer theaterId) {
        List<MoviePlaying> moviesPlaying = moviePlayingRepo.getAllByTheaterId(theaterId);
        List<MoviePlayingDTOFull> moviesPlayingDTOsFull = new ArrayList<>();
        if (moviesPlaying.isEmpty()) {
            return moviesPlayingDTOsFull;
        }
        moviesPlayingDTOsFull = getMoviesPlayingDTOsFull(moviesPlaying);
        return moviesPlayingDTOsFull;
    }

    @Override
    public List<MoviePlayingDTOFull> getAllMoviesPlayingForDate(Date date) {
        List<MoviePlaying> moviesPlaying = moviePlayingRepo.
                getAllByDateEndsIsGreaterThanEqualAndDateStartsIsLessThanEqual(date, date);
        return getMoviesPlayingDTOsFull(moviesPlaying);
    }

    @Override
    public List<MoviePlayingDTOFull> getAllMoviesPlayingForDates(Date dateStarts, Date dateEnds) {
        List<MoviePlaying> moviesPlaying =
                moviePlayingRepo.getAllByDateEndsIsGreaterThanEqualAndDateStartsIsLessThanEqual(dateStarts,dateEnds);
        return getMoviesPlayingDTOsFull(moviesPlaying);
    }

    @Override
    public MoviePlayingDTOMin getMinMoviePlayingInTheater(Integer moviePlayingId) {
        MoviePlaying moviePlaying = getMoviePlaying(moviePlayingId);
        return getMoviePlayingDTOMin(moviePlaying);
    }

    @Override
    public List<MoviePlayingDTOMin> getAllMinMoviesPlayingInTheater(Integer theaterId) {
        List<MoviePlaying> moviesPlaying = moviePlayingRepo.getAllByTheaterId(theaterId);
        List<MoviePlayingDTOMin> moviesPlayingDTOsMin = new ArrayList<>();
        if (!moviesPlaying.isEmpty()) {
            moviesPlayingDTOsMin = getMoviesPlayingDTOsMin(moviesPlaying);
        }
        return moviesPlayingDTOsMin;
    }

    @Override
    public List<MoviePlayingDTOMin> getAllMinMoviesPlayingForDate(Date date) {
        List<MoviePlaying> moviesPlaying = moviePlayingRepo.
                getAllByDateEndsIsGreaterThanEqualAndDateStartsIsLessThanEqual(date, date);
        return getMoviesPlayingDTOsMin(moviesPlaying);
    }

    @Override
    public List<MoviePlayingDTOMin> getAllMinMoviesPlayingForDates(Date dateStarts, Date dateEnds) {
        List<MoviePlaying> moviesPlaying =
                moviePlayingRepo.getAllByDateEndsIsGreaterThanEqualAndDateStartsIsLessThanEqual(dateStarts,dateEnds);
        return getMoviesPlayingDTOsMin(moviesPlaying);
    }
}
