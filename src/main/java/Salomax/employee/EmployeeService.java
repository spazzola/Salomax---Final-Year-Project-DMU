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
        grantPrivileges(employee, employeeDto.getWorkRole().getRole());
        employee.setAssignedStudio(employeeDto.getAssignedStudio());

        return employeeDao.save(employee);
    }

    public Employee createOwner(EmployeeDto employeeDto) {
        String messageException = validateEmployee(employeeDto);
        if (messageException.length() > 1) {
            throw new IllegalArgumentException(messageException);
        }
        Employee admin = buildEmployeeObject(employeeDto);
        admin.setWorkRole(WorkRole.OWNER);

        return employeeDao.save(admin);
    }

    public Employee updateEmployee(EmployeeDto employeeDto) {
        String messageException = validateEmployee(employeeDto);
        if (messageException.length() > 1) {
            throw new IllegalArgumentException(messageException);
        }
        Employee employee = employeeDao.findById(employeeDto.getId())
                .orElseThrow();

        if (!employee.getLogin().equals(employeeDto.getLogin())) {
            employee.setLogin(employeeDto.getLogin());
        }
        if (!employee.getPassword().equals(employeeDto.getPassword())) {
            //TODO implement passwords' comparing
        }
        if (!employee.getName().equals(employeeDto.getName())) {
            employee.setName(employeeDto.getName());
        }
        if (!employee.getSurname().equals(employeeDto.getSurname())) {
            employee.setSurname(employeeDto.getSurname());
        }
        if (!employee.getPhoneNumber().equals(employeeDto.getPhoneNumber())) {
            employee.setPhoneNumber(employeeDto.getPhoneNumber());
        }
        if (!employee.getEmail().equals(employeeDto.getEmail())) {
            employee.setEmail(employeeDto.getEmail());
        }
        if (!employee.getWorkRole().equals(employeeDto.getWorkRole())) {
            employee.setWorkRole(employeeDto.getWorkRole());
        }
        if (!employee.getNote().equals(employeeDto.getNote())) {
            employee.setNote(employeeDto.getNote());
        }

        return employee;
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

    private void grantPrivileges(Employee employee, String workRole) {
        switch (workRole.toUpperCase()) {
            case "OWNER":
                employee.setWorkRole(WorkRole.OWNER);
                break;
            case "ADMIN":
                employee.setWorkRole(WorkRole.ADMIN);
                break;
            case "EMPLOYEE":
                employee.setWorkRole(WorkRole.EMPLOYEE);
                break;
        }
    }

}