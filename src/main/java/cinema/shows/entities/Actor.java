package cinema.shows.entities;

import cinema.shows.dtos.ActorDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "actors")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "First_Name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "Last_Name", nullable = false, length = 45)
    private String lastName;

    @ManyToMany(mappedBy = "actorSet")
    private Set<Movie> movieSet;

    public void addMovie(Movie movie) {
        movieSet.add(movie);
        movie.getActorSet().add(this);
    }

    public Actor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Actor(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Actor(ActorDTO actorDTO) {
        this.firstName = actorDTO.getFirstName();
        this.lastName = actorDTO.getLastName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor actor)) return false;
        return Objects.equals(getFirstName(), actor.getFirstName()) && Objects.equals(getLastName(), actor.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }
}
