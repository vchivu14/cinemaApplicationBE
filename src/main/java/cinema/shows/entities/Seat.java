package cinema.shows.entities;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "seat_row", nullable = false)
    private short seatRow;

    @Column(name = "seat_column", nullable = false)
    private short seatColumn;

    @ManyToOne
    @JoinColumn(name = "Halls_id", referencedColumnName = "id", nullable = false)
    private Hall hall;

    @OneToOne
    private Ticket ticket;

}
