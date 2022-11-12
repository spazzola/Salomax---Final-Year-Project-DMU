package Salomax.employee;

import Salomax.studio.Studio;
import Salomax.userDetails.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends User {

    private int workHours;
    @Enumerated(EnumType.STRING)
    private WorkRole workRole;
    private String note;

    @OneToOne
    @JoinColumn(name = "studio_fk")
    private Studio assignedStudio;

    @Builder
    public Employee(Long id, String login, String password, String name, String surname, String phoneNumber,
                    String email, int workHours, WorkRole workRole, String note, Studio assignedStudio) {
        super(id, login, password, name, surname, phoneNumber, email);
        this.workHours = workHours;
        this.workRole = workRole;
        this.note = note;
        this.assignedStudio = assignedStudio;
    }

    public void setWorkRole(WorkRole workRole) {
        this.workRole = workRole;
    }

}