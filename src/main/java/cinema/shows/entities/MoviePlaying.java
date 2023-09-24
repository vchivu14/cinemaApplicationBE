package cinema.shows.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movies_playing")
public class MoviePlaying {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "Date_Starts", nullable = false)
    private Date dateStarts;

    @Column(name = "Date_Ends", nullable = false)
    private Date dateEnds;

    @Column(name = "Movies_id", nullable = false)
    private int movieId;

    @ManyToOne
    @JoinColumn(name = "Theaters_id", referencedColumnName = "id", nullable = false)
    private Theater theater;

    @OneToMany(mappedBy = "moviePlaying")
    private List<Show> shows;

    public MoviePlaying(int id, Date dateStarts, Date dateEnds, int movieId, Theater theater) {
        this.id = id;
        this.dateStarts = dateStarts;
        this.dateEnds = dateEnds;
        this.movieId = movieId;
        this.theater = theater;
    }

    public MoviePlaying(Date dateStarts, Date dateEnds, int movieId, Theater theater) {
        this.dateStarts = dateStarts;
        this.dateEnds = dateEnds;
        this.movieId = movieId;
        this.theater = theater;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof MoviePlaying that))
            return false;
        return getMovieId() == that.getMovieId() && Objects.equals(getDateStarts(), that.getDateStarts())
                && Objects.equals(getDateEnds(), that.getDateEnds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDateStarts(), getDateEnds(), getMovieId());
    }
}
