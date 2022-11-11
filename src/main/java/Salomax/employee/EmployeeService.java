package Salomax.employee;

import Salomax.userDetails.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@AllArgsConstructor
public class EmployeeService {

    private UserService userService;
    private EmployeeDao employeeDao;

    public Employee createEmployee(EmployeeDto employeeDto) {
        String messageException = validateEmployee(employeeDto);
        if (messageException.length() > 1) {
            throw new IllegalArgumentException(messageException);
        }
        Employee employee = buildEmployeeObject(employeeDto);
        employee.setWorkRole(WorkRole.EMPLOYEE);

        return employeeDao.save(employee);
    }

    public Employee createAdmin(EmployeeDto employeeDto) {
        validateEmployee(employeeDto);
        Employee admin = buildEmployeeObject(employeeDto);
        admin.setWorkRole(WorkRole.ADMIN);

        return employeeDao.save(admin);
    }

    public String validateEmployee(EmployeeDto employeeDto) {
        String messageException = "";
        if (!userService.validateName(employeeDto.getLogin())) {
            messageException += "Bad value of login. ";
        }
        if (!userService.validateName(employeeDto.getPassword())) {
            messageException += "Bad value of password. ";
        }
        if (!userService.validateName(employeeDto.getName())) {
            messageException += "Bad value of admin's name. ";
        }
        if (!userService.validateName(employeeDto.getSurname())) {
            messageException += "Bad value of admin's surname. ";
        }
        if (!userService.validatePhoneNumber(employeeDto.getPhoneNumber())) {
            messageException += "Bad value of admin's phone number. ";
        }
        if (!userService.validateEmail(employeeDto.getEmail())) {
            messageException += "Bad value of admin's email. ";
        }

        return messageException;
    }

    private Employee buildEmployeeObject(EmployeeDto employeeDto) {
        return Employee.builder()
                .login(employeeDto.getLogin())
                .password(employeeDto.getPassword())
                .name(employeeDto.getName())
                .surname(employeeDto.getSurname())
                .phoneNumber(employeeDto.getPhoneNumber())
                .email(employeeDto.getEmail())
                .build();
    }

}