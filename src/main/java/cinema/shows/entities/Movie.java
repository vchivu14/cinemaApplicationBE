package cinema.shows.entities;

import cinema.shows.dtos.InputMovieDTO;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "rating", nullable = true)
    private double rating;

    @Column(name = "min_age", nullable = true)
    private short minAge;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "category_id", nullable = false)
    private int categoryId;

    @Column(name = "image", nullable = false, length = 255)
    private String image;

    @Column(name = "trailer", nullable = true, length = 255)
    private String trailer;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "movie_actors", joinColumns = {
            @JoinColumn(name = "movies_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
                    @JoinColumn(name = "actors_id", referencedColumnName = "id", nullable = false, updatable = false) })
    private Set<Actor> actorSet = new HashSet<>();

    public void addActor(Actor actor) {
        actorSet.add(actor);
        actor.getMovieSet().add(this);
    }

    public Movie(String title, double rating, short minAge, String description, int categoryId) {
        this.title = title;
        this.rating = rating;
        this.minAge = minAge;
        this.description = description;
        this.categoryId = categoryId;
    }

    public Movie(String title, double rating, short minAge, String description, int categoryId, Set<Actor> actorSet) {
        this.title = title;
        this.rating = rating;
        this.minAge = minAge;
        this.description = description;
        this.categoryId = categoryId;
        this.actorSet = actorSet;
    }

    public Movie(InputMovieDTO inputMovieDTO, Set<Actor> actors) {
        this.title = inputMovieDTO.getTitle();
        this.rating = inputMovieDTO.getRating();
        this.minAge = inputMovieDTO.getMinAge();
        this.description = inputMovieDTO.getDescription();
        this.categoryId = inputMovieDTO.getCategoryId();
        this.actorSet = actors;
        this.image = inputMovieDTO.getImage();
        this.trailer = inputMovieDTO.getTrailer();
    }

    public Movie(int id, String title, double rating, short minAge, String description, int categoryId) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.minAge = minAge;
        this.description = description;
        this.categoryId = categoryId;
        this.actorSet = new HashSet<>();
    }

    public Movie(int id, String title, double rating, short minAge, String description, int categoryId, String image,
            String trailer) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.minAge = minAge;
        this.description = description;
        this.categoryId = categoryId;
        this.image = image;
        this.trailer = trailer;
        this.actorSet = new HashSet<>();
    }

    public Movie(String title, double rating, short minAge, String description, int categoryId, String image,
            String trailer) {
        this.title = title;
        this.rating = rating;
        this.minAge = minAge;
        this.description = description;
        this.categoryId = categoryId;
        this.image = image;
        this.trailer = trailer;
    }
}
