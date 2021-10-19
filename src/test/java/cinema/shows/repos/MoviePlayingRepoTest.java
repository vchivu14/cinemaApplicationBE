package cinema.shows.repos;

import cinema.shows.entities.Movie;
import cinema.shows.entities.MoviePlaying;
import cinema.shows.entities.Theater;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class MoviePlayingRepoTest {
    @Autowired
    MoviePlayingRepo moviePlayingRepo;
    @Autowired
    MovieRepo movieRepo;
    @Autowired
    TheaterRepo theaterRepo;
    @Autowired
    CategoryRepo categoryRepo;

    @AfterEach
    public void cleanDB() {
        categoryRepo.deleteAll();
        movieRepo.deleteAll();
        theaterRepo.deleteAll();
        moviePlayingRepo.deleteAll();
    }

    @Test
    @Sql("/createCategory.sql")
    @Sql("/createMovie.sql")
    @Sql("/createTheater.sql")
    public void testAddingMoviePlayingInTheater() {
        Date dateStarts = Date.valueOf("2021-11-11");
        Date dateEnds = Date.valueOf("2021-11-22");
        Movie movie = movieRepo.getById(1);
        Theater theater = theaterRepo.getById(1);
        MoviePlaying moviePlaying = new MoviePlaying(dateStarts, dateEnds, movie.getId(), theater);
        assertEquals(0, moviePlayingRepo.count());
        moviePlayingRepo.save(moviePlaying);
        assertEquals(1, moviePlayingRepo.count());
        moviePlayingRepo.delete(moviePlaying);
        assertEquals(1,movieRepo.count());
        assertEquals(1,theaterRepo.count());
    }

    @Test
    @Sql("/createCategory.sql")
    @Sql("/createMovie.sql")
    @Sql("/createTheater.sql")
    public void testGettingMoviePlayingForASpecificDate() {
        Date dateStarts = Date.valueOf("2021-11-11");
        Date dateEnds = Date.valueOf("2021-11-22");
        Movie movie = movieRepo.getById(1);
        Theater theater = theaterRepo.getById(1);
        MoviePlaying moviePlaying = new MoviePlaying(dateStarts, dateEnds, movie.getId(), theater);
        Date beforeStartDate = Date.valueOf("2021-11-10");
        Date betweenPlayingDates = Date.valueOf("2021-11-16");
        Date afterEndingDate = Date.valueOf("2021-11-23");
        moviePlayingRepo.save(moviePlaying);
        assertEquals(0, moviePlayingRepo.getAllByDateEndsIsGreaterThanEqualAndDateStartsIsLessThanEqual(beforeStartDate, beforeStartDate).size());
        assertEquals(1, moviePlayingRepo.getAllByDateEndsIsGreaterThanEqualAndDateStartsIsLessThanEqual(dateStarts, dateStarts).size());
        assertEquals(1, moviePlayingRepo.getAllByDateEndsIsGreaterThanEqualAndDateStartsIsLessThanEqual(betweenPlayingDates, betweenPlayingDates).size());
        assertEquals(0, moviePlayingRepo.getAllByDateEndsIsGreaterThanEqualAndDateStartsIsLessThanEqual(afterEndingDate, afterEndingDate).size());
    }

    @Test
    @Sql("/createCategory.sql")
    @Sql("/createMovie.sql")
    @Sql("/createTheater.sql")
    public void testGettingMoviePlayingForAPairOFDates() {
        Date dateStarts = Date.valueOf("2021-11-11");
        Date dateEnds = Date.valueOf("2021-11-22");
        Movie movie = movieRepo.getById(1);
        Theater theater = theaterRepo.getById(1);
        MoviePlaying moviePlaying = new MoviePlaying(dateStarts, dateEnds, movie.getId(), theater);
        Date beforeStartDate = Date.valueOf("2021-11-10");
        Date betweenPlayingDates = Date.valueOf("2021-11-16");
        moviePlayingRepo.save(moviePlaying);
        assertEquals(1, moviePlayingRepo.getAllByDateEndsIsGreaterThanEqualAndDateStartsIsLessThanEqual(dateStarts,dateEnds).size());
        assertEquals(1, moviePlayingRepo.getAllByDateEndsIsGreaterThanEqualAndDateStartsIsLessThanEqual(beforeStartDate,dateEnds).size());
        assertEquals(1, moviePlayingRepo.getAllByDateEndsIsGreaterThanEqualAndDateStartsIsLessThanEqual(beforeStartDate,betweenPlayingDates).size());
        assertEquals(1, moviePlayingRepo.getAllByDateEndsIsGreaterThanEqualAndDateStartsIsLessThanEqual(beforeStartDate,dateStarts).size());
        assertEquals(1, moviePlayingRepo.getAllByDateEndsIsGreaterThanEqualAndDateStartsIsLessThanEqual(dateStarts,betweenPlayingDates).size());
        assertEquals(1, moviePlayingRepo.getAllByDateEndsIsGreaterThanEqualAndDateStartsIsLessThanEqual(betweenPlayingDates,dateEnds).size());
        assertEquals(0, moviePlayingRepo.getAllByDateEndsIsGreaterThanEqualAndDateStartsIsLessThanEqual(dateStarts,beforeStartDate).size());
    }

}