package Salomax.studio;

import Salomax.employee.EmployeeDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateStudioResponse {

    private StudioDto studioDto;
    private EmployeeDto employeeDto;

}