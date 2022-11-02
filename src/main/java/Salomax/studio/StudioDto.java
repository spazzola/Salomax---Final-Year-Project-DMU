package Salomax.studio;

import Salomax.address.Address;
import Salomax.client.Client;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudioDto {

    private Long id;
    private String name;
    private String nip;
    private String regon;
    private String phoneNumber;
    private String email;
    private Address address;
    private List<Client> assignedClients;

}