package Salomax.appointment;

import Salomax.appointment.appointmentDetails.AppointmentDetails;
import Salomax.client.Client;
import Salomax.employee.Employee;
import Salomax.studio.Studio;
import Salomax.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long id;

    private String note;

    private BigDecimal price;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime startDate;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private List<AppointmentDetails> appointmentDetails;

    @OneToOne
    @JoinColumn(name = "studio_fk")
    private Studio assignedStudio;

    @OneToOne
    @JoinColumn(name = "client_fk")
    private Client assignedClient;

    @OneToOne
    @JoinColumn(name = "employee_fk")
    private Employee assignedEmployee;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(price, that.price) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(appointmentDetails, that.appointmentDetails) &&
                Objects.equals(assignedStudio, that.assignedStudio) &&
                Objects.equals(assignedClient, that.assignedClient) &&
                Objects.equals(assignedEmployee, that.assignedEmployee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, startDate, endDate, appointmentDetails, assignedStudio, assignedClient, assignedEmployee);
    }
}