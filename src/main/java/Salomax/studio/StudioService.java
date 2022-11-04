package Salomax.studio;

import Salomax.address.AddressService;
import Salomax.employee.Employee;
import Salomax.employee.EmployeeDto;
import Salomax.employee.EmployeeMapper;
import Salomax.employee.WorkRole;
import Salomax.userDetails.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudioService {

    private AddressService addressService;
    private UserService userService;
    private StudioMapper studioMapper;
    private EmployeeMapper employeeMapper;

    public CreateStudioResponse createStudioAndAdmin(CreateStudioRequest createStudioRequest) {
        try {
            validate(createStudioRequest);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }

        Studio studio = createStudio(createStudioRequest);
        Employee admin = createAdmin(createStudioRequest);
        admin.setAssignedStudio(studio);

        StudioDto studioDto = studioMapper.toDto(studio);
        EmployeeDto adminDto = employeeMapper.toDto(admin);

        return CreateStudioResponse.builder()
                .studioDto(studioDto)
                .employeeDto(adminDto)
                .build();
    }

    private void validate(CreateStudioRequest createStudioRequest) {
        String messageException = null;
        if (!userService.validateNameOrSurname(createStudioRequest.getStudioName())) {
            messageException += "Studio's name cannot be empty. ";
        }
        if (!validateNIP(createStudioRequest.getNip())) {
            messageException += "Bad value of NIP number. ";
        }
        if (!validateRegon(createStudioRequest.getRegon())) {
            messageException += "Bad value of REGON number. ";
        }
        if (!userService.validatePhoneNumber(createStudioRequest.getStudioPhoneNumber())) {
            messageException += "Bad value of phone number. ";
        }
        if (!userService.validateEmail(createStudioRequest.getStudioEmail())) {
            messageException += "Bad email. ";
        }
        if (messageException != null) {
            throw new IllegalArgumentException(messageException);
        }

        //TODO validate admin's data


        addressService.validateAddress();
    }

    private Studio createStudio(CreateStudioRequest createStudioRequest) {
        return Studio.builder()
                .name(createStudioRequest.getStudioName())
                .nip(createStudioRequest.getNip())
                .regon(createStudioRequest.getRegon())
                .phoneNumber(createStudioRequest.getStudioPhoneNumber())
                .email(createStudioRequest.getStudioEmail())
                .address(createStudioRequest.getAddress())
                .build();
    }

    private Employee createAdmin(CreateStudioRequest createStudioRequest) {
        return Employee.builder()
                .login(createStudioRequest.getLogin())
                .password(createStudioRequest.getPassword())
                .name(createStudioRequest.getEmployeeName())
                .surname(createStudioRequest.getSurname())
                .phoneNumber(createStudioRequest.getEmployeePhoneNumber())
                .email(createStudioRequest.getEmployeeEmail())
                .workRole(WorkRole.ADMIN.getRole())
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