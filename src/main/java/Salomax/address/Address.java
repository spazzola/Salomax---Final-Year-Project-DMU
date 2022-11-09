package Salomax.address;

import lombok.Builder;

import java.util.Objects;

@Builder
public class Address {

    private String country;
    private String voivodeship;
    private String city;
    private String postalCode;
    private String street;
    private String houseNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(country, address.country) &&
                Objects.equals(voivodeship, address.voivodeship) &&
                Objects.equals(city, address.city) &&
                Objects.equals(postalCode, address.postalCode) &&
                Objects.equals(street, address.street) &&
                Objects.equals(houseNumber, address.houseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, voivodeship, city, postalCode, street, houseNumber);
    }
}