package Salomax.studio;

import Salomax.address.Address;
import Salomax.client.Client;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@Builder
public class Studio {

    private Long id;
    private String name;
    private String nip;
    private String regon;
    private String phoneNumber;
    private String email;
    private Address address;
    private List<Client> assignedClients;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Studio studio = (Studio) o;
        return nip.equals(studio.nip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nip);
    }
}