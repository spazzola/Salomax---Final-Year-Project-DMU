package Salomax.studio;

import Salomax.address.Address;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateStudioRequest {

    private String studioName;
    private String nip;
    private String regon;
    private String phoneNumber;
    private String email;
    private Address address;

    private String login;
    private String password;
    private String employeeName;
    private String surname;

}