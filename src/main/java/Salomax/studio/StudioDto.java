package Salomax.studio;

import Salomax.address.AddressDto;
import Salomax.client.Client;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@Builder
public class StudioDto {

    private Long id;
    private String name;
    private String nip;
    private String regon;
    private String phoneNumber;
    private String email;
    private AddressDto addressDto;
    private List<Client> assignedClients;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudioDto studioDto = (StudioDto) o;
        return nip.equals(studioDto.nip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nip);
    }

}