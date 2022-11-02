package Salomax.employee;

import Salomax.studio.Studio;
import Salomax.userDetails.UserDetails;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
public class Employee extends UserDetails {

    private int workHours;
    private String workRole;
    private String note;
    private Studio assignedStudio;

    @Builder
    public Employee(Long id, String login, String password, String name, String surname, String phoneNumber,
                    String email, int workHours, String workRole, String note, Studio assignedStudio) {
        super(id, login, password, name, surname, phoneNumber, email);
        this.workHours = workHours;
        this.workRole = workRole;
        this.note = note;
        this.assignedStudio = assignedStudio;
    }

    public void setWorkRole(WorkRole workRole) {
        this.workRole = workRole.getRole();
    }

}