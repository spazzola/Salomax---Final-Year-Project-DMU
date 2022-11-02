package Salomax.userDetails;

import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {


    public boolean validateNameOrSurname(String nameOrSurname) {
        return validateString(nameOrSurname);
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        return phoneNumber.length() >= 9 && phoneNumber.length() <= 12;
    }

    public boolean validateEmail(String email) {
        if (email == null || email.equals("") || email.contains(" ")) {
            return false;
        }
        return email.contains("@") && email.contains(".");
    }

    public boolean validateLogin(String login) {
        return validateString(login);
    }

    private boolean validateString(String string) {
        if (string == null) {
            return false;
        }
        else if (string.equals("")) {
            return false;
        }
        else return !string.equals(" ");
    }

}