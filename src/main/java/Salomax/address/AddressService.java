package Salomax.address;

import Salomax.studio.StudioDto;
import Salomax.userDetails.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {

    private UserService userService;
    private AddressDao addressDao;

    public Address createAddress(AddressDto addressDto) {
        String messageException = validateAddress(addressDto);
        if (messageException.length() > 1) {
            throw new IllegalArgumentException(messageException);
        }

        Address address = Address.builder()
                .country(addressDto.getCountry())
                .voivodeship(addressDto.getVoivodeship())
                .city(addressDto.getCity())
                .postalCode(addressDto.getPostalCode())
                .street(addressDto.getStreet())
                .houseNumber(addressDto.getHouseNumber())
                .build();

        return addressDao.save(address);
    }

    public Address updateAddress(AddressDto addressDto) {
        String messageException = validateAddress(addressDto);
        if (messageException.length() > 1) {
            throw new IllegalArgumentException(messageException);
        }

        Address address = addressDao.findById(addressDto.getId())
                .orElseThrow();

        if (!address.getCountry().equals(addressDto.getCountry())) {
            address.setCountry(addressDto.getCountry());
        }
        if (!address.getVoivodeship().equals(addressDto.getVoivodeship())) {
            address.setVoivodeship(addressDto.getVoivodeship());
        }
        if (!address.getCity().equals(addressDto.getCity())) {
            address.setCity(addressDto.getCity());
        }
        if (!address.getPostalCode().equals(addressDto.getPostalCode())) {
            address.setPostalCode(addressDto.getPostalCode());
        }
        if (!address.getStreet().equals(addressDto.getStreet())) {
            address.setStreet(addressDto.getStreet());
        }
        if (!address.getHouseNumber().equals(addressDto.getHouseNumber())) {
            address.setHouseNumber(addressDto.getHouseNumber());
        }

        return addressDao.save(address);
    }

    public String validateAddress(AddressDto addressDto) {
        String messageException = "";
        if (!userService.validateName(addressDto.getCountry())) {
            messageException = "Bad value of country. ";
        }
        if (!userService.validateName(addressDto.getVoivodeship())) {
            messageException = "Bad value of voivodeship. ";
        }
        if (!userService.validateName(addressDto.getCity())) {
            messageException += "Bad value of city. ";
        }
        if (!userService.validateName(addressDto.getPostalCode())) {
            messageException += "Bad value of postal code. ";
        }
        if (!userService.validateName(addressDto.getStreet())) {
            messageException += "Bad value of street ";
        }
        if (!userService.validateName(addressDto.getHouseNumber())) {
            messageException += "Bad value of house number. ";
        }

        return messageException;
    }

}