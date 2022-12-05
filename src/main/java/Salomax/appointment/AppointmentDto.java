package Salomax.appointment;

import Salomax.appointment.appointmentDetails.AppointmentDetailsDto;
import Salomax.client.ClientDto;
import Salomax.employee.EmployeeDto;
import Salomax.studio.StudioDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class AppointmentDto {

    private Long id;
    private String note;
    private BigDecimal price;
    private StudioDto studioDto;
    private ClientDto clientDto;
    private EmployeeDto employeeDto;
    private List<AppointmentDetailsDto> appointmentDetailsDto;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime startDate;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime endDate;

}