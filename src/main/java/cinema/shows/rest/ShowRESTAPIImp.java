package cinema.shows.rest;

import cinema.shows.dtos.InputShowDTO;
import cinema.shows.dtos.ShowDTOMin;
import cinema.shows.services.ShowServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowRESTAPIImp implements ShowRESTAPI {
    private ShowServices showServices;

    public ShowRESTAPIImp(ShowServices services) {
        this.showServices = services;
    }

    @GetMapping
    public ResponseEntity<List<ShowDTOMin>> getMinShowsByTheater(Integer theaterId) {
        List<ShowDTOMin> showDTOsMin = showServices.getMinShowsByTheater(theaterId);
        return new ResponseEntity<>(showDTOsMin, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ShowDTOMin> addShow(@RequestBody InputShowDTO inputShowDTO) {
        ShowDTOMin show = showServices.addShow(inputShowDTO);
        return new ResponseEntity<>(show, HttpStatus.OK);
    }

    @GetMapping("/date")
    public ResponseEntity<List<ShowDTOMin>> getAllShowsForTheaterForDate(
            @RequestParam("date") String date) {
        Date dateLooked = Date.valueOf(date);
        List<ShowDTOMin> showDTOsMin = showServices.getMinShowsByDate(dateLooked);
        return new ResponseEntity<>(showDTOsMin, HttpStatus.OK);
    }

    @GetMapping("/dates")
    public ResponseEntity<List<ShowDTOMin>> getAllShowsForTheaterForDates(
            @RequestParam("dateStart") String dateStart,
            @RequestParam("dateEnd") String dateEnd) {
        Date dateOne = Date.valueOf(dateStart);
        Date dateTwo = Date.valueOf(dateEnd);
        List<ShowDTOMin> showDTOsMin = showServices.getMinShowsByDates(dateOne,dateTwo);
        return new ResponseEntity<>(showDTOsMin, HttpStatus.OK);
    }

}
