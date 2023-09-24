package cinema.shows.configurations;

import cinema.shows.entities.*;
import cinema.shows.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

@Component
public class DataLoader {
    private CategoryRepo categoryRepo;
    private MovieRepo movieRepo;
    private ActorRepo actorRepo;
    private TheaterRepo theaterRepo;
    private MoviePlayingRepo moviePlayingRepo;
    private HallRepo hallRepo;
    private ShowRepo showRepo;

    @Autowired
    public DataLoader(CategoryRepo categoryRepo, MovieRepo movieRepo,
            ActorRepo actorRepo, MoviePlayingRepo moviePlayingRepo,
            TheaterRepo theaterRepo, HallRepo hallRepo, ShowRepo showRepo) {
        this.categoryRepo = categoryRepo;
        this.movieRepo = movieRepo;
        this.actorRepo = actorRepo;
        this.theaterRepo = theaterRepo;
        this.moviePlayingRepo = moviePlayingRepo;
        this.hallRepo = hallRepo;
        this.showRepo = showRepo;
        if (categoryRepo.count() == 0) {
            loadCategories();
            loadMoviesAndActors();
            loadTheater();
            loadMoviesPlaying();
            loadHall();
            loadShows();
        }
    }

    private void loadCategories() {
        categoryRepo.save(new Category(1, "drama"));
        categoryRepo.save(new Category(2, "comedy"));
        categoryRepo.save(new Category(3, "sci-fy"));
    }

    Movie movieOne;
    Movie movieTwo;
    Movie movieThree;
    Theater theater;

    private void loadMoviesAndActors() {
        Actor one = new Actor(1, "Al", "Pacino");
        Actor two = new Actor(2, "Robert", "DeNiro");
        Actor three = new Actor(3, "Jim", "Carrey");
        Actor four = new Actor(4, "Keanu", "Reeves");
        actorRepo.save(one);
        actorRepo.save(two);
        actorRepo.save(three);
        actorRepo.save(four);
        Movie godfather = new Movie(1, "The Godfather", 9, (short) 16, "A classic for any time...", 1,
                "https://images.seoghoer.dk/s3fs-public/media/article/the-godfather.png",
                "https://www.youtube.com/watch?v=sY1S34973zA");
        List<Actor> actorListGodfather = new ArrayList(Arrays.asList(one, two));
        Set<Actor> actorSetGodfather = new HashSet(actorListGodfather);
        godfather.setActorSet(actorSetGodfather);
        movieOne = movieRepo.save(godfather);
        Movie truman = new Movie(2, "The Truman Show", 8, (short) 12, "Something different...", 2,
                "https://images-na.ssl-images-amazon.com/images/S/pv-target-images/1372b1f835edc8dc24851b9f5c749dfcba767dc1e009f23605cd357fb2fb32c4._RI_V_TTW_.jpg",
                "https://www.youtube.com/results?search_query=truemanshow+trailer");
        List<Actor> actorListTruman = new ArrayList(Arrays.asList(three));
        Set<Actor> actorSetTruman = new HashSet(actorListTruman);
        truman.setActorSet(actorSetTruman);
        movieTwo = movieRepo.save(truman);
        Movie matrix = new Movie(3, "The Matrix", 10, (short) 14, "Will blow your mind...", 3,
                "https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_FMjpg_UX1000_.jpg",
                "https://www.youtube.com/watch?v=vKQi3bBA1y8");
        List<Actor> actorListMatrix = new ArrayList(Arrays.asList(four));
        Set<Actor> actorSetMatrix = new HashSet(actorListMatrix);
        matrix.setActorSet(actorSetMatrix);
        movieThree = movieRepo.save(matrix);
    }

    private void loadTheater() {
        theater = theaterRepo.save(new Theater(1, "Norrebro", "Lygten 16", "Copenhagen", (short) 2400));
    }

    MoviePlaying moviePlayingOne;
    MoviePlaying moviePlayingTwo;
    MoviePlaying moviePlayingThree;

    private void loadMoviesPlaying() {
        Date dateStartsGodfather = Date.valueOf("2021-10-15");
        Date dateEndsGodfather = Date.valueOf("2021-10-21");
        MoviePlaying moviePlayingGodfather = new MoviePlaying(dateStartsGodfather, dateEndsGodfather, movieOne.getId(),
                theater);
        moviePlayingOne = moviePlayingRepo.save(moviePlayingGodfather);
        Date dateStartsTrumanShow = Date.valueOf("2021-10-17");
        Date dateEndsTrumanShow = Date.valueOf("2021-10-23");
        MoviePlaying moviePlayingTrumanShow = new MoviePlaying(dateStartsTrumanShow, dateEndsTrumanShow,
                movieTwo.getId(), theater);
        moviePlayingTwo = moviePlayingRepo.save(moviePlayingTrumanShow);
        Date dateStartsMatrix = Date.valueOf("2021-10-19");
        Date dateEndsMatrix = Date.valueOf("2021-10-25");
        MoviePlaying moviePlayingMatrix = new MoviePlaying(dateStartsMatrix, dateEndsMatrix, movieThree.getId(),
                theater);
        moviePlayingThree = moviePlayingRepo.save(moviePlayingMatrix);
    }

    private void loadHall() {
        Hall hall = new Hall(1, "Room One", 1);
        hallRepo.save(hall);
    }

    private void loadShows() {
        Show showOneGodfather = new Show(Date.valueOf("2021-10-16"), Time.valueOf("15:00:00"), moviePlayingOne, 1);
        showRepo.save(showOneGodfather);
    }
}
