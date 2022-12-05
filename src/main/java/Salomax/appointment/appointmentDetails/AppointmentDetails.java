package Salomax.appointment.appointmentDetails;

import Salomax.appointment.Appointment;
import Salomax.work.Work;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "appointment_details")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_details_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_fk")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "work_fk")
    private Work work;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentDetails that = (AppointmentDetails) o;
        return Objects.equals(work, that.work);
    }

    @Override
    public int hashCode() {
        return Objects.hash(work);
    }

    @Override
    public String toString() {
        return "AppointmentDetails{" +
                "id=" + id +
                ", work=" + work +
                '}';
    }
}