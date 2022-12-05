package Salomax.appointment;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/appointment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AppointmentController {

    private AppointmentMapper appointmentMapper;
    private AppointmentService appointmentService;

    @PostMapping("/create")
    public CreateAppointmentResponse createAppointment(@RequestBody CreateAppointmentRequest createAppointmentRequest) {
        return appointmentService.createAppointment(createAppointmentRequest);
    }

}