package Salomax.employee;

import Salomax.user.User;
import lombok.Builder;
import lombok.Data;

@Data
public class EmployeeDto extends User {

    private int workHours;
    private WorkRole workRole;
    private String note;
    private Long assignedStudioId;

    @Builder
    public EmployeeDto(Long id, String login, String password, String name, String surname, String phoneNumber,
                    String email, int workHours, WorkRole workRole, String note, Long assignedStudioId) {
        super(id, login, password, name, surname, phoneNumber, email);
        this.workHours = workHours;
        this.workRole = workRole;
        this.note = note;
        this.assignedStudioId = assignedStudioId;
    }

}