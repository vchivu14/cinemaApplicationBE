package cinema.shows.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShowDTOFull {
    private MoviePlayingDTOFull moviePlayingDTOFull;
    private Time time;
    private Date date;
    private String hall;
    private String hallId;
}
