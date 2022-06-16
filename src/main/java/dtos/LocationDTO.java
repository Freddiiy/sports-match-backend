package dtos;

import entities.Location;

public class LocationDTO {
    private String address;
    private String city;

    public LocationDTO() {}

    public LocationDTO(String address, String city) {
        this.address = address;
        this.city = city;
    }

    public LocationDTO(Location location) {
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
