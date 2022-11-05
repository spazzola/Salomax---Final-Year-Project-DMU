package Salomax.address;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class AddressDto {

    private String country;
    private String voivodeship;
    private String city;
    private String postalCode;
    private String street;
    private String houseNumber;

}