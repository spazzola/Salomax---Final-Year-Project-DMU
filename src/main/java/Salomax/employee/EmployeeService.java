package Salomax.employee;

import Salomax.userDetails.UserDetailsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@AllArgsConstructor
public class EmployeeService {

    private UserDetailsService userDetailsService;

    public Employee createEmployee(EmployeeDto employeeDto) {
        validate(employeeDto);

        return Employee.builder()
                .login(employeeDto.getLogin())
                .password(employeeDto.getPassword())
                .name(employeeDto.getName())
                .surname(employeeDto.getSurname())
                .phoneNumber(employeeDto.getPhoneNumber())
                .email(employeeDto.getEmail())
                .build();
    }

    private void validate(EmployeeDto employeeDto) {
        String messageException = null;
//        if (userDetailsService.validateNameOrSurname(employeeDto.getLogin())) {
//            messageException += "Employee's login cannot be empty. ";
//        }
        if (userDetailsService.validateNameOrSurname(employeeDto.getName())) {
            messageException = "Employee's name cannot be empty. ";
        }
//        if (userDetailsService.validateNameOrSurname(employeeDto.getSurname())) {
//            messageException += "Employee's surname cannot be empty. ";
//        }
        if (messageException != null) {
            throw new IllegalArgumentException(messageException);
        }
    }

}