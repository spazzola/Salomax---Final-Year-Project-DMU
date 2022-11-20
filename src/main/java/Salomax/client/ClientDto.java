package Salomax.client;

import Salomax.user.User;
import lombok.Builder;
import lombok.Data;

@Data
public class ClientDto extends User {

    @Builder
    public ClientDto(Long id, String login, String password, String name, String surname,
                  String phoneNumber, String email) {
        super(id, login, password, name, surname, phoneNumber, email);
    }

}