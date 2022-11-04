package Salomax.studio;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudioMapper {

    public StudioDto toDto(Studio studio) {
        return StudioDto.builder()
                .id(studio.getId())
                .name(studio.getName())
                .nip(studio.getNip())
                .regon(studio.getRegon())
                .phoneNumber(studio.getPhoneNumber())
                .email(studio.getEmail())
                .address(studio.getAddress())
                .assignedClients(studio.getAssignedClients())
                .build();
    }

    public List<StudioDto> toDto(List<Studio> studios) {
        return studios.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}