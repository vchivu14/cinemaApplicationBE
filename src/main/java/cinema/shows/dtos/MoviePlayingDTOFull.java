package cinema.shows.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoviePlayingDTOFull {
    private Integer moviePlayingId;
    private MovieDTOFull movieDTOFull;
    private Date dateStarts;
    private Date dateEnds;
    private String theater;
    private Integer theaterId;
}
