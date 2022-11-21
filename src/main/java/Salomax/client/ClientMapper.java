package Salomax.client;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapper {


    public ClientDto toDto(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .login(client.getLogin())
                .password(client.getPassword())
                .name(client.getName())
                .surname(client.getSurname())
                .phoneNumber(client.getPhoneNumber())
                .email(client.getEmail())
                .build();
    }

    public List<ClientDto> toDto(List<Client> clients) {
        return clients.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}