package cinema.shows.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputMovieDTO {
    @Size(min = 3, message = "String must have at least 3 characters")
    private String title;
    private Double rating;
    private Short minAge;
    private String description;
    private Integer categoryId;
    private String trailer;
    private String image;
    private List<ActorDTO> actorList;
}
