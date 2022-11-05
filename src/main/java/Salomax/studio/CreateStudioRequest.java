package Salomax.studio;

import Salomax.address.Address;
import Salomax.address.AddressDto;
import Salomax.employee.EmployeeDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateStudioRequest {

    private StudioDto studioDto;
    private EmployeeDto employeeDto;
    private AddressDto addressDto;

}