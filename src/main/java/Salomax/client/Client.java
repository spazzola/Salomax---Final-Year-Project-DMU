package Salomax.client;

import Salomax.studio.Studio;
import Salomax.userDetails.UserDetails;

public class Client extends UserDetails {

    public Client(Long id, String login, String password, String name, String surname,
                  String phoneNumber, String email, Studio assignedStudio) {
        super(id, login, password, name, surname, phoneNumber, email, assignedStudio);
    }

}