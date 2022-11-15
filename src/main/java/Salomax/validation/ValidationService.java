package Salomax.validation;

import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    public boolean validateString(String string) {
        if (string == null) {
            return false;
        } else if (string.equals("")) {
            return false;
        } else return !string.equals(" ");
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

    public boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }
        if (password.length() < 5) {
            return false;
        }
        if (!checkIfPasswordContainsSpecialCharacter(password)) {
            return false;
        }
        if (!checkIfPasswordContainsDigit(password)) {
            return false;
        }
        if (!checkIfPasswordContainsUpperCase(password)) {
            return false;
        }
        if (!checkIfPasswordContainsLowerCase(password)) {
            return false;
        }

        return true;
    }

    private boolean checkIfPasswordContainsSpecialCharacter(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (!Character.isDigit(password.charAt(i)) &&
                    !Character.isLetter(password.charAt(i)) &&
                    !Character.isUpperCase(password.charAt(i)) &&
                    !Character.isLowerCase(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfPasswordContainsDigit(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfPasswordContainsUpperCase(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfPasswordContainsLowerCase(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

}