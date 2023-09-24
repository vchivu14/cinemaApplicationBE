package cinema.shows.unit;
import cinema.shows.entities.Movie;
import cinema.shows.entities.Actor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {

    @Test
    public void testAddActor() {
        // Create a sample Movie instance
        Movie movie = new Movie("Sample Movie", 8.0, (short) 12, "Sample description", 1);

        // Create a sample Actor instance
        Actor actor = new Actor("John", "Doe");

        // Add the actor to the movie
        movie.addActor(actor);

        // Verify that the actor is added to the movie's actorSet
        assertTrue(movie.getActorSet().contains(actor));

        // Verify that the actor's movieSet contains the movie
        assertTrue(actor.getMovieSet().contains(movie));
    }

    @Test
    public void testConstructor() {
        // Create a Movie instance using a constructor
        Movie movie = new Movie("Sample Movie", 8.0, (short) 12, "Sample description", 1);

        // Verify that the fields are correctly set
        assertEquals("Sample Movie", movie.getTitle());
        assertEquals(8.0, movie.getRating());
        assertEquals(12, movie.getMinAge());
        assertEquals("Sample description", movie.getDescription());
        assertEquals(1, movie.getCategoryId());
        assertNull(movie.getImage());
        assertNull(movie.getTrailer());
    }

    // Add more test cases as needed to cover other constructors and methods
}