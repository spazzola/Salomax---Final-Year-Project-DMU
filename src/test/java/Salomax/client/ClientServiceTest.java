package Salomax.client;

import Salomax.employee.Employee;
import Salomax.employee.EmployeeDto;
import Salomax.employee.WorkRole;
import Salomax.user.User;
import Salomax.validation.ValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

    private String VALID_LOGIN;
    private String VALID_PASSWORD;
    private String VALID_NAME;
    private String VALID_SURNAME;
    private String VALID_PHONE_NUMBER;
    private String VALID_EMAIL;
    private User VALID_CLIENT;
    private ClientService clientService;
    @Mock
    private ClientDao clientDao;

    @Before
    public void setUp() {
        ValidationService validationService = new ValidationService();
        mock(ClientDao.class);
        clientService = new ClientService(clientDao, validationService);

        VALID_LOGIN = "abc";
        VALID_PASSWORD = "123!Ac";
        VALID_NAME = "xyz";
        VALID_SURNAME = "qwe";
        VALID_PHONE_NUMBER = "123456789";
        VALID_EMAIL = "test@x.z";

        VALID_CLIENT = Client.builder()
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build();

        when(clientDao.save(Mockito.any(Client.class))).then(returnsFirstArg());
        when(clientDao.findById(1L)).thenReturn(Optional.of(Client.builder()
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build()));
    }


    @Test
    public void createClientValidDataShouldPass() {
        //given
        ClientDto clientDto = ClientDto.builder()
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();

        //when
        User client = clientService.createClient(clientDto);

        //then
        assertEquals(VALID_CLIENT, client);
    }

    @Test
    public void updateClientCheckIfNameWasUpdated() {
        //given
        ClientDto clientDto = ClientDto.builder()
                .id(1L)
                .name("new name")
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();

        //when
        User client = clientService.updateClient(clientDto);

        //then
        assertEquals("new name", client.getName());
    }

    @Test
    public void updateClientCheckIfSurnameWasUpdated() {
        //given
        ClientDto clientDto = ClientDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .surname("new surname")
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();

        //when
        User client = clientService.updateClient(clientDto);

        //then
        assertEquals("new surname", client.getSurname());
    }

    @Test
    public void updateClientCheckIfPhoneNumberWasUpdated() {
        //given
        ClientDto clientDto = ClientDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber("444444444")
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();

        //when
        User client = clientService.updateClient(clientDto);

        //then
        assertEquals("444444444", client.getPhoneNumber());
    }

    @Test
    public void updateClientCheckIfMailWasUpdated() {
        //given
        ClientDto clientDto = ClientDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email("newmail@x.l")
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();

        //when
        User client = clientService.updateClient(clientDto);

        //then
        assertEquals("newmail@x.l", client.getEmail());
    }

    @Test
    public void updateClientCheckIfLoginWasUpdated() {
        //given
        ClientDto clientDto = ClientDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login("newLogin")
                .password(VALID_PASSWORD)
                .build();

        //when
        User client = clientService.updateClient(clientDto);

        //then
        assertEquals("newLogin", client.getLogin());
    }

    @Test
    public void updateClientCheckIfPasswordWasUpdated() {
        //given
        ClientDto clientDto = ClientDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password("newPassword1!")
                .build();

        //when
        User client = clientService.updateClient(clientDto);

        //then
        assertEquals("newPassword1!", client.getPassword());
    }

}