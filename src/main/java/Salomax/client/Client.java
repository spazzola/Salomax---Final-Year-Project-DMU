package Salomax.client;

import Salomax.assignedClients.AssignedClients;
import Salomax.employee.WorkRole;
import Salomax.studio.Studio;
import Salomax.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client extends User {

    @OneToMany(mappedBy = "client")
    private List<AssignedClients> assignedClients;

    @Builder
    public Client(Long id, String login, String password, String name, String surname,
                  String phoneNumber, String email) {
        super(id, login, password, name, surname, phoneNumber, email);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

}