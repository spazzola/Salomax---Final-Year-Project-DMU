package Salomax.client;

import Salomax.validation.ValidationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Data
@Service
public class ClientService {

    private ClientDao clientDao;
    private ValidationService validationService;

    public Client createClient(ClientDto clientDto) {
        validate(clientDto);

        Client client = Client.builder()
                .login(clientDto.getLogin())
                .password(clientDto.getPassword())
                .name(clientDto.getName())
                .surname(clientDto.getSurname())
                .phoneNumber(clientDto.getPhoneNumber())
                .email(clientDto.getEmail())
                .build();

        return clientDao.save(client);
    }

    public Client updateClient(ClientDto clientDto) {
        validate(clientDto);

        Client client = clientDao.findById(clientDto.getId())
                .orElseThrow();

        if (!client.getLogin().equals(clientDto.getLogin())) {
            client.setLogin(clientDto.getLogin());
        }
        if (!client.getPassword().equals(clientDto.getPassword())) {
            //TODO implement passwords' comparing
        }
        if (!client.getName().equals(clientDto.getName())) {
            client.setName(clientDto.getName());
        }
        if (!client.getSurname().equals(clientDto.getSurname())) {
            client.setSurname(clientDto.getSurname());
        }
        if (!client.getPhoneNumber().equals(clientDto.getPhoneNumber())) {
            client.setPhoneNumber(clientDto.getPhoneNumber());
        }
        if (!client.getEmail().equals(clientDto.getEmail())) {
            client.setEmail(clientDto.getEmail());
        }

        return client;
    }

    private void validate(ClientDto clientDto) {
        String messageException = validateClient(clientDto);
        if (messageException.length() > 1) {
            throw new IllegalArgumentException(messageException);
        }
    }

    public String validateClient(ClientDto clientDto) {
        String messageException = "";
        if (!validationService.validateString(clientDto.getLogin())) {
            messageException += "Bad value of login. ";
        }
        if (!validationService.validatePassword(clientDto.getPassword())) {
            messageException += "Bad value of password. ";
        }
        if (!validationService.validateString(clientDto.getName())) {
            messageException += "Bad value of  client's name. ";
        }
        if (!validationService.validateString(clientDto.getSurname())) {
            messageException += "Bad value of client's surname. ";
        }
        if (!validationService.validatePhoneNumber(clientDto.getPhoneNumber())) {
            messageException += "Bad value of client's phone number. ";
        }
        if (!validationService.validateEmail(clientDto.getEmail())) {
            messageException += "Bad value of client's email. ";
        }

        return messageException;
    }
}