package Salomax.studio;

import Salomax.address.AddressDto;
import Salomax.address.AddressMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class StudioMapper {

    private AddressMapper addressMapper;

    public StudioDto toDto(Studio studio) {
        AddressDto addressDto = addressMapper.toDto(studio.getAddress());
        return StudioDto.builder()
                .id(studio.getId())
                .name(studio.getName())
                .nip(studio.getNip())
                .regon(studio.getRegon())
                .phoneNumber(studio.getPhoneNumber())
                .email(studio.getEmail())
                .addressDto(addressDto)
                .build();
    }

    public List<StudioDto> toDto(List<Studio> studios) {
        return studios.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}