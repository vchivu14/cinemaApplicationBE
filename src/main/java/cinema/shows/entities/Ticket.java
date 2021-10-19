package cinema.shows.entities;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "Price", nullable = false)
    private double price;

    @Column(name = "Seats_Number", nullable = false, length = 45)
    private String seatsNumber;

    @ManyToOne
    @JoinColumn(name = "Shows_id", referencedColumnName = "id", nullable = false)
    private Show show;

}
