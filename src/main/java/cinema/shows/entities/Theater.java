package cinema.shows.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "theaters")
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "Name", nullable = false, length = 45)
    private String name;

    @Column(name = "Street", nullable = false, length = 45)
    private String street;

    @Column(name = "City", nullable = false, length = 45)
    private String city;

    @Column(name = "Zipcode", nullable = false)
    private short zipcode;

    @OneToMany(mappedBy = "theater")
    private List<MoviePlaying> moviesPlaying;

    public Theater(String name, String street, String city, short zipcode) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
    }

    public Theater(int id, String name, String street, String city, short zipcode) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
    }

}
