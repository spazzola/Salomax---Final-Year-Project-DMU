package Salomax.address;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class AddressMapper {


    public AddressDto toDto(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .country(address.getCountry())
                .voivodeship(address.getVoivodeship())
                .city(address.getCity())
                .postalCode(address.getPostalCode())
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .build();
    }

    public List<AddressDto> toDto(List<Address> addresses) {
        return addresses.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}