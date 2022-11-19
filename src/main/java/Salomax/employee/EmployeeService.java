package Salomax.employee;

import Salomax.studio.Studio;
import Salomax.studio.StudioDao;
import Salomax.validation.ValidationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@AllArgsConstructor
public class EmployeeService {

    private ValidationService validationService;
    private EmployeeDao employeeDao;
    private StudioDao studioDao;

    public Employee createEmployee(EmployeeDto employeeDto) {
        validate(employeeDto);

        Employee employee = buildEmployeeObject(employeeDto);
        employee.setWorkRole(employeeDto.getWorkRole());

        Studio studio = studioDao.findById(employeeDto.getAssignedStudioId())
                        .orElseThrow();
        employee.setAssignedStudio(studio);

        return employeeDao.save(employee);
    }

    public Employee createOwner(EmployeeDto employeeDto) {
        validate(employeeDto);

        Employee admin = buildEmployeeObject(employeeDto);
        admin.setWorkRole(WorkRole.OWNER);

        return employeeDao.save(admin);
    }

    public Employee updateEmployee(EmployeeDto employeeDto) {
        validate(employeeDto);

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
        if (!validationService.validateString(employeeDto.getLogin())) {
            messageException += "Bad value of login. ";
        }
        if (!validationService.validatePassword(employeeDto.getPassword())) {
            messageException += "Bad value of password. ";
        }
        if (!validationService.validateString(employeeDto.getName())) {
            messageException += "Bad value of admin's name. ";
        }
        if (!validationService.validateString(employeeDto.getSurname())) {
            messageException += "Bad value of admin's surname. ";
        }
        if (!validationService.validatePhoneNumber(employeeDto.getPhoneNumber())) {
            messageException += "Bad value of admin's phone number. ";
        }
        if (!validationService.validateEmail(employeeDto.getEmail())) {
            messageException += "Bad value of admin's email. ";
        }

        return messageException;
    }

    private void validate(EmployeeDto employeeDto) {
        String messageException = validateEmployee(employeeDto);
        if (messageException.length() > 1) {
            throw new IllegalArgumentException(messageException);
        }
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