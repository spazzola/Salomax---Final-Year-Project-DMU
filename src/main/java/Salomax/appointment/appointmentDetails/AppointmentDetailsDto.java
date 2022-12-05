package Salomax.appointment.appointmentDetails;

import Salomax.work.WorkDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AppointmentDetailsDto {

    private Long id;
    private WorkDto workDto;

}
