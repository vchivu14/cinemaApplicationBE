package cinema.shows.rest;

import cinema.shows.dtos.EditMoviePlayingDTO;
import cinema.shows.dtos.InputMoviePlayingDTO;
import cinema.shows.dtos.MoviePlayingDTOFull;
import cinema.shows.dtos.MoviePlayingDTOMin;
import cinema.shows.services.MoviePlayingServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/playing")
public class MoviePlayingRESTAPIImp implements MoviePlayingRESTAPI {
    private MoviePlayingServices moviePlayingServices;

    public MoviePlayingRESTAPIImp(MoviePlayingServices moviePlayingServices) {
        this.moviePlayingServices = moviePlayingServices;
    }

    @GetMapping
    public ResponseEntity<List<MoviePlayingDTOFull>> getAllMoviesPlayingForTheater(
            @RequestParam("theaterId") int theaterId) {
        List<MoviePlayingDTOFull> moviePlayingDTOFsFull =
                moviePlayingServices.getAllMoviesPlayingInTheater(theaterId);
        System.out.println(moviePlayingDTOFsFull);
        return new ResponseEntity<>(moviePlayingDTOFsFull, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MoviePlayingDTOMin> addMoviePlayingForTheater(
            @RequestBody InputMoviePlayingDTO inputMoviePlayingDTO) {
        MoviePlayingDTOMin moviePlayingDTOMin =
                moviePlayingServices.addMoviePlayingInTheater(inputMoviePlayingDTO);
        return new ResponseEntity<>(moviePlayingDTOMin, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public void removeMoviePlaying(@PathVariable int id) {
        moviePlayingServices.removeMoviePlayingInTheater(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<MoviePlayingDTOMin> editMoviePlayingForTheater(
            @RequestBody InputMoviePlayingDTO inputMoviePlayingDTO,
            @PathVariable int id) {
        MoviePlayingDTOMin moviePlayingDTOMin = moviePlayingServices.updateMoviePlayingInTheater(inputMoviePlayingDTO, id);
        return new ResponseEntity<>(moviePlayingDTOMin, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<MoviePlayingDTOMin> editMoviePlayingForTheater(
            @RequestBody EditMoviePlayingDTO editMoviePlayingDTO) {
        MoviePlayingDTOMin moviePlayingDTOMin = moviePlayingServices.updateMoviePlayingInTheater(editMoviePlayingDTO);
        return new ResponseEntity<>(moviePlayingDTOMin, HttpStatus.OK);
    }

    @GetMapping("/date")
    public ResponseEntity<List<MoviePlayingDTOFull>> getAllMoviesPlayingForDate(
            @RequestParam("date") String date) {
        Date dateLooked = Date.valueOf(date);
        List<MoviePlayingDTOFull> moviePlaying = moviePlayingServices.getAllMoviesPlayingForDate(dateLooked);
        return new ResponseEntity<>(moviePlaying, HttpStatus.OK);
    }

    @GetMapping("/dates")
    public ResponseEntity<List<MoviePlayingDTOFull>> getAllMoviesPlayingForDates(
            @RequestParam("dateStarts") String dateStarts,
            @RequestParam("dateEnds") String dateEnds) {
        Date dateStarting = Date.valueOf(dateStarts);
        Date dateEnding = Date.valueOf(dateEnds);
        List<MoviePlayingDTOFull> moviePlaying = moviePlayingServices.getAllMoviesPlayingForDates(dateStarting,dateEnding);
        return new ResponseEntity<>(moviePlaying, HttpStatus.OK);
    }
}
