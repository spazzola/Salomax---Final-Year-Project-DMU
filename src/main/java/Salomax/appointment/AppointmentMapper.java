package Salomax.appointment;

import Salomax.appointment.appointmentDetails.AppointmentDetailsDto;
import Salomax.appointment.appointmentDetails.AppointmentDetailsMapper;
import Salomax.client.ClientDto;
import Salomax.client.ClientMapper;
import Salomax.employee.EmployeeDto;
import Salomax.employee.EmployeeMapper;
import Salomax.studio.StudioDto;
import Salomax.studio.StudioMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AppointmentMapper {

    private StudioMapper studioMapper;
    private ClientMapper clientMapper;
    private EmployeeMapper employeeMapper;
    private AppointmentDetailsMapper appointmentDetailsMapper;

    public AppointmentDto toDto(Appointment appointment) {
        StudioDto studioDto = studioMapper.toDto(appointment.getAssignedStudio());
        ClientDto clientDto = clientMapper.toDto(appointment.getAssignedClient());
        EmployeeDto employeeDto = employeeMapper.toDto(appointment.getAssignedEmployee());
        List<AppointmentDetailsDto> appointmentDetailsDto = appointmentDetailsMapper.toDto(appointment.getAppointmentDetails());

        return AppointmentDto.builder()
                .id(appointment.getId())
                .note(appointment.getNote())
                .price(appointment.getPrice())
                .studioDto(studioDto)
                .clientDto(clientDto)
                .employeeDto(employeeDto)
                .appointmentDetailsDto(appointmentDetailsDto)
                .startDate(appointment.getStartDate())
                .endDate(appointment.getEndDate())
                .build();
    }

}