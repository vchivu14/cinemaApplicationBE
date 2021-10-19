package cinema.shows.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieDTOFull {
    private Integer id;
    private String title;
    private Double rating;
    private Short minAge;
    private String description;
    private Integer categoryId;
    private String category;
    private List<ActorDTO> actorList;
}
