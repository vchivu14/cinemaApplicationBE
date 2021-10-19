package cinema.shows.rest;

import cinema.shows.dtos.MovieDTOFull;
import cinema.shows.repos.CategoryRepo;
import cinema.shows.repos.MovieRepo;
import cinema.shows.testUtils.TestDataMaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = {cinema.shows.CinemaApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieRESTAPIImpTest {
    @Autowired
    MovieRepo movieRepo;
    @Autowired
    CategoryRepo categoryRepo;

    private final String BASE_PATH = "/api/movies";
    private final HttpHeaders headers = new HttpHeaders();

    @Value("${local.server.port}")
    private int port;

    @Autowired
    TestRestTemplate restTemplate;


    private String makeUrl(String path){
        System.out.println(port);
        return "http://localhost:"+port+path;
    }

    private static int movieId;

    @BeforeEach
    public void setupDatabase(){
        categoryRepo.deleteAll();
        movieRepo.deleteAll();
        int categoryId = TestDataMaker.createCategory(categoryRepo);
        movieId = TestDataMaker.createMovie(movieRepo, categoryId);
    }

    @Test
    void getMovie() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<MovieDTOFull> responseEntity = restTemplate.exchange(makeUrl(BASE_PATH+"/"+movieId),
                HttpMethod.GET,
                entity,
                MovieDTOFull.class);
        assertEquals("Godfather", Objects.requireNonNull(responseEntity.getBody()).getTitle());
    }

}
