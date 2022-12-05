package Salomax.appointment.appointmentDetails;

import Salomax.work.WorkDto;
import Salomax.work.WorkMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AppointmentDetailsMapper {

    private WorkMapper workMapper;

    public AppointmentDetailsDto toDto(AppointmentDetails appointmentDetails) {
        WorkDto workDto = workMapper.toDto(appointmentDetails.getWork());

        return AppointmentDetailsDto.builder()
                .id(appointmentDetails.getId())
                .workDto(workDto)
                .build();
    }

    public List<AppointmentDetailsDto> toDto(List<AppointmentDetails> appointmentDetails) {
        return appointmentDetails.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}