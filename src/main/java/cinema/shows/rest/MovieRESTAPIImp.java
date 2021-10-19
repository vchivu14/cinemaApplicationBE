package cinema.shows.rest;

import cinema.shows.dtos.InputMovieDTO;
import cinema.shows.dtos.MovieDTOFull;
import cinema.shows.services.MovieServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieRESTAPIImp implements MovieRESTAPI {
    private MovieServices movieServices;

    public MovieRESTAPIImp(MovieServices movieServices) {
        this.movieServices = movieServices;
    }

    @GetMapping
    public ResponseEntity<List<MovieDTOFull>> getAllMovies() {
        List<MovieDTOFull> movieDTOsFull = movieServices.getAllMovies();
        return new ResponseEntity<>(movieDTOsFull, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MovieDTOFull> addMovie(@RequestBody InputMovieDTO movieDTO) {
        MovieDTOFull movie = movieServices.addMovie(movieDTO);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTOFull> getMovie(@PathVariable int id) {
        MovieDTOFull movie = movieServices.getMovieDTOFull(id);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<MovieDTOFull> updateMovie(@RequestBody MovieDTOFull movieDTO,
                                                    @RequestParam(required = false) Boolean replace) {
        MovieDTOFull movie = movieServices.updateMovie(movieDTO, replace);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void removeMovie(@PathVariable int id) {
        movieServices.removeMovie(id);
    }
}
