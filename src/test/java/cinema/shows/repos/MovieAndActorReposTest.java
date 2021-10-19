package cinema.shows.repos;

import cinema.shows.entities.Actor;
import cinema.shows.entities.Movie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class MovieAndActorReposTest {
    @Autowired
    MovieRepo movieRepo;
    @Autowired
    ActorRepo actorRepo;

    @AfterEach
    public void cleanDB() {
        movieRepo.deleteAll();
        actorRepo.deleteAll();
    }

    @Test
    @Sql("/createCategory.sql")
    @Sql("/createMovie.sql")
    @Sql("/createActor.sql")
    public void testBidirectionalRel() {
        //check the sql scripts worked
        assertEquals(1, actorRepo.count());
        assertEquals(1, movieRepo.count());
        //getting one actor from the db
        Actor actorOne = actorRepo.getById(1);
        //creating new actor
        Actor actorTwo = new Actor("Robert","De Niro");
        //actor yet not saved
        assertEquals(1, actorRepo.count());
        //getting the movie from the db
        Movie movieOne = movieRepo.getById(1);
        //set the movie's actor list
        List<Actor> actors = new ArrayList<>(Arrays.asList(actorOne, actorTwo));
        movieOne.setActorSet(new HashSet(actors));
        //check there are no actors for that movie yet since we didn't save the movie yet
        assertEquals(0, movieRepo.getById(1).getActorSet().size());
        //check there is only one saved actor in the db yet
        assertEquals(1, actorRepo.count());
        //save the movie
        movieRepo.save(movieOne);
        //now we have two movies
        assertEquals(2, movieRepo.getById(1).getActorSet().size());
        // AND TWO Actors BECAUSE OF THE BIDIRECTIONAL WAY OF SAVING provided by HIBERNATE
        assertEquals(2, actorRepo.count());
        // Querying the actors side we should get a list of the movies he's playing in
        assertEquals(1, actorRepo.getById(1).getMovieSet().size());
    }
}