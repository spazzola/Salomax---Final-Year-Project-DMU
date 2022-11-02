package Salomax.studio;

import Salomax.address.AddressService;
import Salomax.employee.Employee;
import Salomax.employee.WorkRole;
import Salomax.userDetails.UserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudioService {

    private AddressService addressService;
    private UserDetailsService userDetailsService;

    public Studio createStudioAndAdmin(CreateStudioRequest createStudioRequest) {
        try {
            validate(createStudioRequest);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }

        //create studio
        Studio studio = Studio.builder()
                .name(createStudioRequest.getStudioName())
                .nip(createStudioRequest.getNip())
                .regon(createStudioRequest.getRegon())
                .phoneNumber(createStudioRequest.getPhoneNumber())
                .email(createStudioRequest.getEmail())
                .address(createStudioRequest.getAddress())
                .build();

        //create employee with admin privileges and assign it to the studio
        Employee employee = Employee.builder()
                .build();

        employee.setWorkRole(WorkRole.ADMIN);

        return studio;
    }

    private void validate(CreateStudioRequest createStudioRequest) {
        String messageException = null;
        if (!userDetailsService.validateNameOrSurname(createStudioRequest.getStudioName())) {
            messageException += "Studio's name cannot be empty. ";
        }
        if (!validateNIP(createStudioRequest.getNip())) {
            messageException += "Bad value of NIP number. ";
        }
        if (!validateRegon(createStudioRequest.getRegon())) {
            messageException += "Bad value of REGON number. ";
        }
        if (!userDetailsService.validatePhoneNumber(createStudioRequest.getPhoneNumber())) {
            messageException += "Bad value of phone number. ";
        }
        if (!userDetailsService.validateEmail(createStudioRequest.getEmail())) {
            messageException += "Bad email. ";
        }
        if (messageException != null) {
            throw new IllegalArgumentException(messageException);
        }

        addressService.validateAddress();
    }

    private boolean validateNIP(String nip) {
        if (nip == null) {
            return false;
        }
        int nsize = nip.length();
        if (nsize != 10) {
            return false;
        }
        int[] weights = { 6, 5, 7, 2, 3, 4, 5, 6, 7 };
        int j, sum = 0, control;
        int csum = Integer.parseInt(nip.substring(nsize - 1));
        for (int i = 0; i < nsize - 1; i++) {
            char c = nip.charAt(i);
            j = Integer.parseInt(String.valueOf(c));
            sum += j * weights[i];
        }
        control = sum % 11;
        return (control == csum);
    }

    private boolean validateRegon(String regon) {
        if (regon == null) {
            return false;
        }
        int rsize = regon.length();
        if (!((rsize == 9) || (rsize == 14))) {
            return false;
        }

        int[] weights = { 8, 9, 2, 3, 4, 5, 6, 7 };
        int[] weights14 = { 2, 4, 8, 5, 0, 9, 7, 3, 6, 1, 2, 4, 8 };

        if (rsize == 14) {
            weights = weights14;
        }

        int j, sum = 0, control;
        int csum = Integer.parseInt(regon.substring(rsize - 1));
        for (int i = 0; i < rsize - 1; i++) {
            char c = regon.charAt(i);
            j = Integer.parseInt(String.valueOf(c));
            sum += j * weights[i];
        }

        control = sum % 11;
        if (control == 10) {
            control = 0;
        }
        return (control == csum);
    }

}