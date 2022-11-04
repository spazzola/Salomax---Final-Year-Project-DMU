package Salomax.client;

import Salomax.userDetails.User;

public class Client extends User {

    public Client(Long id, String login, String password, String name, String surname,
                  String phoneNumber, String email) {
        super(id, login, password, name, surname, phoneNumber, email);
    }

}