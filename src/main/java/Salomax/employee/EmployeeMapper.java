package Salomax.employee;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    public EmployeeDto toDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .login(employee.getLogin())
                .password(employee.getPassword())
                .name(employee.getName())
                .surname(employee.getSurname())
                .phoneNumber(employee.getPhoneNumber())
                .email(employee.getEmail())
                .workHours(employee.getWorkHours())
                .workRole(employee.getWorkRole())
                .note(employee.getNote())
                .assignedStudio(employee.getAssignedStudio())
                .build();
    }

    public List<EmployeeDto> toDto(List<Employee> employees) {
        return employees.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}