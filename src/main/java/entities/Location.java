package entities;

import dtos.LocationDTO;

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

    public Location(LocationDTO location) {
        this.address = location.getAddress();
        this.city = location.getCity();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

