package Salomax.studio;

import Salomax.address.AddressService;
import Salomax.employee.*;
import Salomax.userDetails.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudioService {

    private AddressService addressService;
    private UserService userService;
    private EmployeeService employeeService;
    private StudioMapper studioMapper;
    private EmployeeMapper employeeMapper;

    public CreateStudioResponse createStudioAndAdmin(CreateStudioRequest createStudioRequest) {
        try {
            validateRequest(createStudioRequest);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }

        Studio studio = createStudio(createStudioRequest);
        Employee admin = employeeService.createAdmin(createStudioRequest.getEmployeeDto());
        admin.setAssignedStudio(studio);

        StudioDto studioDto = studioMapper.toDto(studio);
        EmployeeDto adminDto = employeeMapper.toDto(admin);

        return CreateStudioResponse.builder()
                .studioDto(studioDto)
                .employeeDto(adminDto)
                .build();
    }

    private void validateRequest(CreateStudioRequest createStudioRequest) {
        String messageException = "";

        messageException = validateStudio(createStudioRequest.getStudioDto());
        messageException += employeeService.validateEmployee(createStudioRequest.getEmployeeDto());
        messageException += addressService.validateAddress(createStudioRequest.getAddressDto());

        if (!messageException.equals("")) {
            throw new IllegalArgumentException(messageException);
        }
    }

    private String validateStudio(StudioDto studioDto) {
        String messageException = "";
        if (!userService.validateName(studioDto.getName())) {
            messageException += "Bad value of studio's name. ";
        }
        if (!validateNIP(studioDto.getNip())) {
            messageException += "Bad value of NIP number. ";
        }
        if (!validateRegon(studioDto.getRegon())) {
            messageException += "Bad value of REGON number. ";
        }
        if (!userService.validatePhoneNumber(studioDto.getPhoneNumber())) {
            messageException += "Bad value of studio's phone number. ";
        }
        if (!userService.validateEmail(studioDto.getEmail())) {
            messageException += "Bad value of studio's email. ";
        }

        return messageException;
    }

    private Studio createStudio(CreateStudioRequest createStudioRequest) {
        StudioDto studioDto = createStudioRequest.getStudioDto();
        return Studio.builder()
                .name(studioDto.getName())
                .nip(studioDto.getNip())
                .regon(studioDto.getRegon())
                .phoneNumber(studioDto.getPhoneNumber())
                .email(studioDto.getEmail())
                .address(studioDto.getAddress())
                .build();
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