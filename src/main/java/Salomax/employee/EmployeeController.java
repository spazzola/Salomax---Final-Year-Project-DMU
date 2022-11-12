package Salomax.employee;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeController {

    private EmployeeService employeeService;
    private EmployeeMapper employeeMapper;


    @PostMapping("/create")
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeService.createEmployee(employeeDto);
        return employeeMapper.toDto(employee);
    }

    @PutMapping("/update")
    public EmployeeDto updateEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeService.updateEmployee(employeeDto);
        return employeeMapper.toDto(employee);
    }

}