package cinema.shows.dtos;

import cinema.shows.entities.Actor;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActorDTO {
    private String firstName;
    private String lastName;

    public ActorDTO(Actor actor) {
        this.firstName = actor.getFirstName();
        this.lastName = actor.getLastName();
    }
}
