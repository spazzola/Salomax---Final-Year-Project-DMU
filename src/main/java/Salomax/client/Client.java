package Salomax.client;

import Salomax.assignedClients.AssignedClients;
import Salomax.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client extends User {

    @OneToMany(mappedBy = "client")
    private List<AssignedClients> assignedClients;

    public Client(Long id, String login, String password, String name, String surname,
                  String phoneNumber, String email) {
        super(id, login, password, name, surname, phoneNumber, email);
    }

}