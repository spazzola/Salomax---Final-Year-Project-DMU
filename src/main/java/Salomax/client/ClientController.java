package Salomax.client;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClientController {

    private ClientService clientService;
    private ClientMapper clientMapper;


    @PostMapping("/create")
    public ClientDto createEmployee(@RequestBody ClientDto clientDto) {
        Client client = clientService.createClient(clientDto);
        return clientMapper.toDto(client);
    }

    @PutMapping("/update")
    public ClientDto updateEmployee(@RequestBody ClientDto clientDto) {
        Client client = clientService.updateClient(clientDto);
        return clientMapper.toDto(client);
    }

}