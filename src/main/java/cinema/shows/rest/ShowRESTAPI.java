package cinema.shows.rest;

import cinema.shows.dtos.InputShowDTO;
import cinema.shows.dtos.ShowDTOMin;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ShowRESTAPI {
    ResponseEntity<List<ShowDTOMin>> getMinShowsByTheater(Integer theaterId);
    ResponseEntity<ShowDTOMin> addShow(InputShowDTO inputShowDTO);
    ResponseEntity<List<ShowDTOMin>> getAllShowsForTheaterForDate(String date);
    ResponseEntity<List<ShowDTOMin>> getAllShowsForTheaterForDates(String dateOne, String dateTwo);

}
