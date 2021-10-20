package cinema.shows.services;

import cinema.shows.dtos.*;
import cinema.shows.entities.Actor;
import cinema.shows.entities.Movie;
import cinema.shows.exceptions.ResourceNotFoundException;
import cinema.shows.repos.CategoryRepo;
import cinema.shows.repos.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MovieServicesImp implements MovieServices {
    private final MovieRepo movieRepo;
    @Autowired
    ActorServices actorServices;
    @Autowired
    CategoryRepo categoryRepo;

    public MovieServicesImp(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }

    private String errorMessage(Integer id){
        return "Resource Not found with id = " + id;
    }

    @Override
    public Movie getMovieById(Integer movieId) {
        return movieRepo.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(movieId)));
    }

    @Override
    public MovieDTOMin getMovieDTOMinFromMovie(Movie movie) {
        MovieDTOMin movieDTOMin = new MovieDTOMin();
        movieDTOMin.setTitle(movie.getTitle());
        movieDTOMin.setMinAge(movie.getMinAge());
        String category = categoryRepo.getById(movie.getCategoryId()).getName();
        movieDTOMin.setCategory(category);
        return movieDTOMin;
    }

    @Override
    public MovieDTOFull getMovieDTOFullFromMovie(Movie movie) {
        MovieDTOFull movieDTOFull = new MovieDTOFull();
        movieDTOFull.setId(movie.getId());
        movieDTOFull.setTitle(movie.getTitle());
        movieDTOFull.setCategoryId(movie.getCategoryId());
        String category = categoryRepo.getById(movie.getCategoryId()).getName();
        movieDTOFull.setCategory(category);
        movieDTOFull.setMinAge(movie.getMinAge());
        movieDTOFull.setDescription(movie.getDescription());
        movieDTOFull.setRating(movie.getRating());
        if (!movie.getActorSet().isEmpty()) {
            Set<Actor> actorSet = movie.getActorSet();
            movieDTOFull.setActorList(actorServices.getListOfActorsToShowWithMovieRequest(actorSet));
        } else {
            movieDTOFull.setActorList(new ArrayList<>());
        }
        return movieDTOFull;
    }

    @Override
    public MovieDTOFull getMovieDTOFull(Integer movieId) {
        Movie movie = getMovieById(movieId);
        return getMovieDTOFullFromMovie(movie);
    }

    @Override
    public MovieDTOFull addMovie(InputMovieDTO inputMovieDTO) {
        Set<Actor> actors;
        if (inputMovieDTO.getActorList().isEmpty() || inputMovieDTO.getActorList() == null) {
            actors = new HashSet<>();
        } else {
            actors = actorServices.getSetOfActorsFromListOfActorDTOs(inputMovieDTO.getActorList());
        }
        Movie newMovie = new Movie(inputMovieDTO, actors);
        Movie movieSaved = movieRepo.save(newMovie);
        return getMovieDTOFullFromMovie(movieSaved);
    }

    @Override
    public MovieDTOFull updateMovie(MovieDTOFull movieDTO, Boolean replace) {
        Movie movieInDB = getMovieById(movieDTO.getId());
        String title = movieDTO.getTitle();
        Double rating = movieDTO.getRating();
        Short minAge = movieDTO.getMinAge();
        String description = movieDTO.getDescription();
        Integer categoryId = movieDTO.getCategoryId();
        List<ActorDTO> actors = movieDTO.getActorList();
        if (title != null) {
            movieInDB.setTitle(title);
        }
        if (rating != null) {
            movieInDB.setRating(rating);
        }
        if (minAge != null) {
            movieInDB.setMinAge(minAge);
        }
        if (description != null) {
            movieInDB.setDescription(description);
        }
        if (categoryId != null) {
            movieInDB.setCategoryId(categoryId);
        }
        if (actors != null) {
            Set<Actor> actorSet = actorServices.getSetOfActorsFromListOfActorDTOs(actors);
            if (replace) {
                movieInDB.setActorSet(actorSet);
            } else {
                movieInDB.getActorSet().addAll(actorSet);
            }
        }
        Movie movieSaved = movieRepo.save(movieInDB);
        return getMovieDTOFullFromMovie(movieSaved);
    }

    @Override
    public void removeMovie(Integer movieId) {
        getMovieById(movieId);
        movieRepo.deleteById(movieId);
    }

    @Override
    public List<MovieDTOFull> getAllMovies() {
        List<Movie> movies = movieRepo.findAll();
        List<MovieDTOFull> movieDTOsFull = new ArrayList<>();
        for (Movie movie: movies) {
            movieDTOsFull.add(getMovieDTOFullFromMovie(movie));
        }
        return movieDTOsFull;
    }

    @Override
    public List<MovieDTOMin> getAllMinMovies() {
        List<Movie> movies = movieRepo.findAll();
        List<MovieDTOMin> movieDTOsMin = new ArrayList<>();
        for (Movie movie: movies) {
            movieDTOsMin.add(getMovieDTOMinFromMovie(movie));
        }
        return movieDTOsMin;
    }
}
