package Salomax.address;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressDto {

    private Long id;
    private String country;
    private String voivodeship;
    private String city;
    private String postalCode;
    private String street;
    private String houseNumber;

}