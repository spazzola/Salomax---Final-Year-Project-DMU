package Salomax.appointment;

import Salomax.appointment.appointmentDetails.AppointmentDetails;
import Salomax.client.Client;
import Salomax.client.ClientDao;
import Salomax.employee.Employee;
import Salomax.employee.EmployeeDao;
import Salomax.studio.Studio;
import Salomax.studio.StudioDao;
import Salomax.work.Work;
import Salomax.work.WorkDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AppointmentService {

    private EmployeeDao employeeDao;
    private ClientDao clientDao;
    private StudioDao studioDao;
    private WorkDao workDao;
    private AppointmentDao appointmentDao;
    private AppointmentMapper appointmentMapper;


    public CreateAppointmentResponse createAppointment(CreateAppointmentRequest createAppointmentRequest) {
        //validate
        try {
            validate(createAppointmentRequest);
        } catch (IllegalArgumentException e) {
            return CreateAppointmentResponse.builder()
                    .messageException(e.getMessage())
                    .build();
        }

        Appointment appointment = new Appointment();
        appointment.setStartDate(createAppointmentRequest.getStartDate());
        appointment.setNote(createAppointmentRequest.getNote());

        List<Work> works = workDao.findAllById(createAppointmentRequest.getWorkIds());

        LocalDateTime endDate = calculateEndDate(createAppointmentRequest.getStartDate(), works);
        appointment.setEndDate(endDate);

        BigDecimal price = calculatePrice(works);
        appointment.setPrice(price);

        List<AppointmentDetails> appointmentDetailsList = createAppointmentDetails(works, appointment);
        appointment.setAppointmentDetails(appointmentDetailsList);

        assignClientEmployeeStudio(createAppointmentRequest, appointment);

        appointmentDao.save(appointment);
        return createResponse(appointment);
    }

    private void validate(CreateAppointmentRequest createAppointmentRequest) {
        String messageException = "";
        if (createAppointmentRequest.getStartDate() == null) {
            messageException += "Start date cannot be a null. ";
        }
        if (createAppointmentRequest.getStudioId() <= 0) {
            messageException += "Bad value of studioId. ";
        }
        if (createAppointmentRequest.getClientId() <= 0) {
            messageException += "Bad value of clientId. ";
        }
        if (createAppointmentRequest.getEmployeeId() <= 0) {
            messageException += "Bad value of employeeId. ";
        }

        messageException += validateWorkIds(createAppointmentRequest.getWorkIds());
        messageException += validateIfDateCollides(createAppointmentRequest.getStartDate(), createAppointmentRequest.getWorkIds());

        if (messageException.length() > 1) {
            throw new IllegalArgumentException(messageException);
        }

        //return messageException;
    }

    private String validateWorkIds(List<Long> workIds) {
        String messageException = "";
        if (workIds == null) {
            messageException += "List with workIds cannot be a null. ";
        } else {
            if (workIds.size() == 0) {
                messageException += "List with workIds cannot be empty. ";
            }
            byte wrongIdsCounter = (byte) workIds.stream()
                    .filter(id -> id <= 0)
                    .count();
            if (wrongIdsCounter > 0) {
                messageException += "Bad value of workId. ";
            }
        }

        return messageException;
    }

    private String validateIfDateCollides(LocalDateTime startDate, List<Long> workIds) {
        String messageException = "";
        List<Work> works = workDao.findAllById(workIds);
        LocalDateTime endDate = calculateEndDate(startDate, works);

        messageException = checkIfDatesOverlap(startDate, endDate);

        return messageException;
    }

    private LocalDateTime calculateEndDate(LocalDateTime startDate, List<Work> works) {
        byte totalHoursDuration;
        byte totalMinutesDuration;

        totalHoursDuration = (byte) works.stream().mapToInt(Work::getHoursDuration).sum();
        totalMinutesDuration = (byte) works.stream().mapToInt(Work::getMinutesDuration).sum();

        return startDate.plusHours(totalHoursDuration).plusMinutes(totalMinutesDuration);
    }

    private void assignClientEmployeeStudio(CreateAppointmentRequest createAppointmentRequest, Appointment appointment) {
        Employee employee = employeeDao.findById(createAppointmentRequest.getEmployeeId())
                .orElseThrow();
        appointment.setAssignedEmployee(employee);

        Client client = clientDao.findById(createAppointmentRequest.getClientId())
                .orElseThrow();
        appointment.setAssignedClient(client);

        Studio studio = studioDao.findById(createAppointmentRequest.getStudioId())
                .orElseThrow();
        appointment.setAssignedStudio(studio);
    }

    private List<AppointmentDetails> createAppointmentDetails(List<Work> works, Appointment appointment) {
        List<AppointmentDetails> appointmentDetailsList = new ArrayList<>();
        for (Work work : works) {
            AppointmentDetails appointmentDetails = AppointmentDetails.builder()
                    .appointment(appointment)
                    .work(work)
                    .build();
            appointmentDetailsList.add(appointmentDetails);
        }

        return appointmentDetailsList;
    }

    private BigDecimal calculatePrice(List<Work> works) {
        BigDecimal price = BigDecimal.ZERO;
        for (Work work : works) {
            price = price.add(work.getPrice());
        }

        return price;
    }

    private CreateAppointmentResponse createResponse(Appointment appointment) {
        AppointmentDto appointmentDto = appointmentMapper.toDto(appointment);
        return CreateAppointmentResponse.builder()
                .appointmentDto(appointmentDto)
                .build();
    }

    private String checkIfDatesOverlap(LocalDateTime startDate, LocalDateTime endDate) {
        Optional<List<Appointment>> appointments = appointmentDao.findByStartDateBetween(startDate, endDate);
        String messageException = "";
        if (appointments.isPresent()) {
            if (appointments.get().size() > 0) {
                messageException = "Date of an another appointment collides with given appointment. ";
            }
        }

        appointments = appointmentDao.findByEndDateBetween(startDate, endDate);
        if (appointments.isPresent()) {
            if (appointments.get().size() > 0) {
                messageException = "Date of an another appointment collides with given appointment. ";
            }
        }

        return messageException;
    }

}