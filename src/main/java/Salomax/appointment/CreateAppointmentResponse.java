package Salomax.appointment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAppointmentResponse {

    private AppointmentDto appointmentDto;
    private String messageException;

}