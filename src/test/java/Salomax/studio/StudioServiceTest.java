package Salomax.studio;

import Salomax.address.Address;
import Salomax.address.AddressService;
import Salomax.client.Client;
import Salomax.userDetails.UserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@SpringBootTest
public class StudioServiceTest {

    // VALID TEST DATA
    private String VALID_STUDIO_NAME;
    private String VALID_NIP;
    private String VALID_REGON;
    private String VALID_PHONE_NUMBER;
    private String VALID_EMAIL;
    private Address VALID_ADDRESS;

    private String VALID_LOGIN;
    private String VALID_PASSWORD;
    private String VALID_EMPLOYEE_NAME;
    private String VALID_EMPLOYEE_SURNAME;
    private List<Client> VALID_ASSIGNED_CLIENTS;
    private Studio VALID_STUDIO;
    private StudioService studioService;

    @Before
    public void setUp() {
        AddressService addressService = new AddressService();
        UserDetailsService userDetailsService = new UserDetailsService();
        studioService = new StudioService(addressService, userDetailsService);

        VALID_STUDIO_NAME = "BeautyS";
        VALID_NIP = "3818483497";
        VALID_REGON = "058205731";
        VALID_PHONE_NUMBER = "123456789";
        VALID_EMAIL = "test@t.x";
        VALID_LOGIN = "xyz";
        VALID_PASSWORD = "GM4l!";
        VALID_EMPLOYEE_NAME = "qwerty";
        VALID_EMPLOYEE_SURNAME = "idk";
        VALID_ADDRESS = Address.builder().build();
        VALID_ASSIGNED_CLIENTS = new ArrayList<>();

        VALID_STUDIO = Studio.builder()
                .name(VALID_STUDIO_NAME)
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .address(VALID_ADDRESS)
                .assignedClients(VALID_ASSIGNED_CLIENTS)
                .build();
    }

    @Test
    public void createStudioValidDataShouldPass() {
        //given
        Address address = Address.builder().build();
        //List<Client> assignedClients = new ArrayList<>();
        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioName("BeautyS")
                .nip("3818483497")
                .regon("058205731")
                .phoneNumber("123456789")
                .email("test@t.x")
                .address(address)
                .login("xyz")
                .password("GM4l!")
                .employeeName("qwerty")
                .surname("idk")
                .build();

        //when
        Studio studio = studioService.createStudioAndAdmin(createStudioRequest);

        //then
        assertEquals(VALID_STUDIO, studio);
    }

    @Test
    public void createStudioInvalidNameShouldThrowError() {
        //given
        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioName(null)
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .address(VALID_ADDRESS)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .employeeName(VALID_EMPLOYEE_NAME)
                .surname(VALID_EMPLOYEE_SURNAME)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> studioService.createStudioAndAdmin(createStudioRequest));
    }

    @Test
    public void createStudioInvalidNipLengthShouldThrowError() {
        //given
        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioName(VALID_STUDIO_NAME)
                .nip("381848349")
                .regon(VALID_REGON)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .address(VALID_ADDRESS)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .employeeName(VALID_EMPLOYEE_NAME)
                .surname(VALID_EMPLOYEE_SURNAME)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> studioService.createStudioAndAdmin(createStudioRequest));
    }

    @Test
    public void createStudioInvalidNipValueShouldThrowError() {
        //given
        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioName(VALID_STUDIO_NAME)
                .nip("3818483496")
                .regon(VALID_REGON)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .address(VALID_ADDRESS)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .employeeName(VALID_EMPLOYEE_NAME)
                .surname(VALID_EMPLOYEE_SURNAME)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> studioService.createStudioAndAdmin(createStudioRequest));
    }

    @Test
    public void createStudioInvalidRegonValueShouldThrowError() {
        //given
        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioName(VALID_STUDIO_NAME)
                .nip(VALID_NIP)
                .regon("058205732")
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .address(VALID_ADDRESS)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .employeeName(VALID_EMPLOYEE_NAME)
                .surname(VALID_EMPLOYEE_SURNAME)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> studioService.createStudioAndAdmin(createStudioRequest));
    }

    @Test
    public void createStudioTooShortPhoneNumberShouldThrowError() {
        //given
        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioName(VALID_STUDIO_NAME)
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .phoneNumber("1234569")
                .email(VALID_EMAIL)
                .address(VALID_ADDRESS)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .employeeName(VALID_EMPLOYEE_NAME)
                .surname(VALID_EMPLOYEE_SURNAME)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> studioService.createStudioAndAdmin(createStudioRequest));
    }

    @Test
    public void createStudioWrongEmailShouldThrowError() {
        //given
        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioName(VALID_STUDIO_NAME)
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email("dsds.pl")
                .address(VALID_ADDRESS)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .employeeName(VALID_EMPLOYEE_NAME)
                .surname(VALID_EMPLOYEE_SURNAME)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> studioService.createStudioAndAdmin(createStudioRequest));
    }

}