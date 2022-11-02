package Salomax.employee;

import Salomax.studio.Studio;
import Salomax.userDetails.UserDetails;
import lombok.Builder;
import lombok.Data;

@Data
public class EmployeeDto extends UserDetails {

    private int workHours;
    private String workRole;
    private String note;

    @Builder
    public EmployeeDto(Long id, String login, String password, String name, String surname, String phoneNumber,
                    String email, int workHours, String workRole, String note, Studio assignedStudio) {
        super(id, login, password, name, surname, phoneNumber, email, assignedStudio);
        this.workHours = workHours;
        this.workRole = workRole;
        this.note = note;
    }

}