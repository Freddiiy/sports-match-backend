package entities;

import javax.persistence.*;

@Entity
@Table(name = "LOCATION")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private String city;

    public Location() {}

    public Location(String address, String city) {
        this.address = address;
        this.city = city;
    }
}

