package cinema.shows.testUtils;

import cinema.shows.entities.Category;
import cinema.shows.entities.Movie;
import cinema.shows.repos.CategoryRepo;
import cinema.shows.repos.MovieRepo;

public class TestDataMaker {
    public static int createCategory(CategoryRepo categoryRepo){
        categoryRepo.deleteAll();
        return categoryRepo.save(new Category(1,"drama")).getId();
    }

    public static int createMovie(MovieRepo movieRepo, int categoryId){
        movieRepo.deleteAll();
        return movieRepo.save(new Movie("Godfather",10,(short)2,"a classic...",categoryId)).getId();
    }
}
