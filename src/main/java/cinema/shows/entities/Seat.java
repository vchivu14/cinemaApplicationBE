package cinema.shows.entities;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @Column(name = "Number", nullable = false, length = 45)
    private String number;

    @Column(name = "row", nullable = false)
    private short row;

    @Column(name = "column", nullable = false)
    private short column;

    @ManyToOne
    @JoinColumn(name = "Halls_id", referencedColumnName = "id", nullable = false)
    private Hall hall;

}
