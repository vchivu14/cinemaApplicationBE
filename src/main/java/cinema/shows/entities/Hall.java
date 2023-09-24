package cinema.shows.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "halls")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "tag", nullable = false, length = 255)
    private String tag;

    @Column(name = "theaters_id", nullable = false)
    private int theaterId;

    @OneToMany(mappedBy = "hall")
    private Set<Seat> seats;

    public Hall(int id, String tag, int theaterId) {
        this.id = id;
        this.tag = tag;
        this.theaterId = theaterId;
    }
}
