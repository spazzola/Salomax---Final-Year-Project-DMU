package Salomax.address;

import lombok.Builder;

@Builder
public class Address {

    private String country;
    private String voivodeship;
    private String city;
    private String postalCode;
    private String street;
    private String houseNumber;

}