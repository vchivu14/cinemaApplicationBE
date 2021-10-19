package cinema.shows.services;

import cinema.shows.dtos.*;
import cinema.shows.entities.*;
import cinema.shows.exceptions.ResourceNotFoundException;
import cinema.shows.repos.HallRepo;
import cinema.shows.repos.ShowRepo;
import cinema.shows.repos.TheaterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowServicesImp implements ShowServices {
    private ShowRepo showRepo;
    @Autowired
    HallRepo hallRepo;
    @Autowired
    MoviePlayingServices moviePlayingServices;
    @Autowired
    TheaterRepo theaterRepo;

    public ShowServicesImp(ShowRepo showRepo) {
        this.showRepo = showRepo;
    }

    private String errorMessage(Long showId){
        return "Show NOT FOUND for Id = " + showId;
    }

    @Override
    public Show getShow(Long showId) {
        return showRepo.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(showId)));
    }

    private Show getShowFromInputShowDTO(InputShowDTO inputShowDTO) {
        Show show = new Show();
        show.setDate(Date.valueOf(inputShowDTO.getDate()));
        show.setTime(Time.valueOf(inputShowDTO.getTime()));
        show.setHallId(inputShowDTO.getHallId());
        MoviePlaying moviePlaying = moviePlayingServices.getMoviePlaying(inputShowDTO.getMoviePlayingId());
        show.setMoviePlaying(moviePlaying);
        return show;
    }
    private ShowDTOMin getShowDTOMinFromShow(Show show) {
        ShowDTOMin showDTOMin = new ShowDTOMin();
        showDTOMin.setDate(show.getDate());
        showDTOMin.setTime(show.getTime().toString());
        MoviePlayingDTOMin moviePlayingDTOMin =
                moviePlayingServices.getMinMoviePlayingInTheater(show.getMoviePlaying().getId());
        showDTOMin.setMoviePlayingDTOMin(moviePlayingDTOMin);
        String hall = hallRepo.getById(show.getHallId()).getTag();
        showDTOMin.setHall(hall);
        return showDTOMin;
    }
    private List<ShowDTOMin> getShowDTOsMinFromShows(List<Show> shows) {
        List<ShowDTOMin> showDTOsMin = new ArrayList<>();
        for (Show s: shows) {
            showDTOsMin.add(getShowDTOMinFromShow(s));
        }
        return showDTOsMin;
    }
    private ShowDTOFull getShowDTOFullFromShow(Show show) {
        ShowDTOFull showDTOFull = new ShowDTOFull();
        showDTOFull.setDate(show.getDate());
        showDTOFull.setTime(show.getTime());
        MoviePlayingDTOFull moviePlayingDTOFull =
                moviePlayingServices.getMoviePlayingInTheater(show.getMoviePlaying().getId());
        String hall = hallRepo.getById(show.getHallId()).getTag();
        showDTOFull.setHall(hall);
        showDTOFull.setMoviePlayingDTOFull(moviePlayingDTOFull);
        return showDTOFull;
    }
    private List<ShowDTOFull> getShowDTOsFullFromShows(List<Show> shows) {
        List<ShowDTOFull> showDTOsFull = new ArrayList<>();
        for (Show s: shows) {
            showDTOsFull.add(getShowDTOFullFromShow(s));
        }
        return showDTOsFull;
    }

    @Override
    public ShowDTOMin addShow(InputShowDTO inputShowDTO) {
        Show show = showRepo.findByDateAndTimeAndHallId
                (Date.valueOf(inputShowDTO.getDate()),
                        Time.valueOf(inputShowDTO.getTime()),
                        inputShowDTO.getHallId());
        if (show == null) {
            Show newShow = getShowFromInputShowDTO(inputShowDTO);
            Show showSaved = showRepo.save(newShow);
            return getShowDTOMinFromShow(showSaved);
        }
        return getShowDTOMinFromShow(show);
    }

    @Override
    public ShowDTOMin updateShow(InputShowDTO inputShowDTO) {
        Integer hallId = inputShowDTO.getHallId();
        Integer moviePlayingId = inputShowDTO.getMoviePlayingId();
        Show showInDB = showRepo.getByMoviePlaying_IdAndHallId(moviePlayingId,hallId);
        Date date = Date.valueOf(inputShowDTO.getDate());
        Time time = Time.valueOf(inputShowDTO.getTime());
        if (date != null) {
            showInDB.setDate(date);
        }
        if (time != null) {
            showInDB.setTime(time);
        }
        if (hallId != null) {
            showInDB.setHallId(hallId);
        }
        if (moviePlayingId != null) {
            MoviePlaying moviePlaying = moviePlayingServices.getMoviePlaying(moviePlayingId);
            showInDB.setMoviePlaying(moviePlaying);
        }
        Show show = showRepo.findByDateAndTimeAndHallId
                (showInDB.getDate(),showInDB.getTime(),showInDB.getHallId());
        if (show == null) {
            Show showSaved = showRepo.save(showInDB);
            return getShowDTOMinFromShow(showSaved);
        }
        return getShowDTOMinFromShow(showInDB);
    }

    @Override
    public void removeShow(Long showId) {
        getShow(showId);
        showRepo.deleteById(showId);
    }

    @Override
    public ShowDTOMin getMinShow(Long showId) {
        Show show = getShow(showId);
        return getShowDTOMinFromShow(show);
    }

    @Override
    public List<ShowDTOMin> getMinShowsByDate(Date date) {
        List<Show> shows = showRepo.getAllByDateIs(date);
        return getShowDTOsMinFromShows(shows);
    }

    @Override
    public List<ShowDTOMin> getMinShowsByDates(Date dateStarts, Date dateEnds) {
        List<Show> shows = showRepo.getAllByDateIsBetween(dateStarts,dateEnds);
        return getShowDTOsMinFromShows(shows);
    }

    @Override
    public List<ShowDTOMin> getMinShowsByTheater(Integer theaterId) {
        Theater theater = theaterRepo.getById(theaterId);
        List<Show> shows = showRepo.getAllByMoviePlaying_TheaterIs(theater);
        return getShowDTOsMinFromShows(shows);
    }

    @Override
    public List<ShowDTOMin> getMinShowsByDateAndTheater(Date date, Integer theaterId) {
        Theater theater = theaterRepo.getById(theaterId);
        List<Show> shows = showRepo.getAllByDateIsAndMoviePlaying_TheaterIs(date, theater);
        return getShowDTOsMinFromShows(shows);
    }

    @Override
    public List<ShowDTOMin> getMinShowsByDatesAndTheater(Date dateOne, Date dateTwo, Integer theaterId) {
        Theater theater = theaterRepo.getById(theaterId);
        List<Show> shows = showRepo.getAllByDateIsBetweenAndMoviePlaying_TheaterIs(dateOne,dateTwo,theater);
        return getShowDTOsMinFromShows(shows);
    }

    @Override
    public List<ShowDTOFull> getShowsByDateAndTheater(Date date, Integer theaterId) {
        Theater theater = theaterRepo.getById(theaterId);
        List<Show> shows = showRepo.getAllByDateIsAndMoviePlaying_TheaterIs(date, theater);
        return getShowDTOsFullFromShows(shows);
    }

    @Override
    public List<ShowDTOFull> getShowsByDatesAndTheater(Date dateOne, Date dateTwo, Integer theaterId) {
        Theater theater = theaterRepo.getById(theaterId);
        List<Show> shows = showRepo.getAllByDateIsBetweenAndMoviePlaying_TheaterIs(dateOne,dateTwo,theater);
        return getShowDTOsFullFromShows(shows);
    }
}
