package cinema.shows.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputMovieDTO {
    private String title;
    private Double rating;
    private Short minAge;
    private String description;
    private Integer categoryId;
    private String trailer;
    private String image;
    private List<ActorDTO> actorList;
}
