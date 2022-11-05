package Salomax.address;

import Salomax.userDetails.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {

    private UserService userService;


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