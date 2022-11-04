package Salomax.studio;

import Salomax.address.Address;
import Salomax.address.AddressService;
import Salomax.employee.EmployeeMapper;
import Salomax.userDetails.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class StudioServiceTest {

    // VALID TEST DATA
    private String VALID_STUDIO_NAME;
    private String VALID_NIP;
    private String VALID_REGON;
    private String VALID_PHONE_NUMBER;
    private String VALID_EMAIL;
    //private Address VALID_ADDRESS;
    private String VALID_LOGIN;
    private String VALID_PASSWORD;
    private String VALID_EMPLOYEE_NAME;
    private String VALID_EMPLOYEE_SURNAME;
    private StudioDto VALID_STUDIO;
    private StudioService studioService;
    private UserService userService;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private StudioMapper studioMapper;

    @Before
    public void setUp() {
        AddressService addressService = new AddressService();
        userService = new UserService();
        employeeMapper = new EmployeeMapper();
        studioMapper = new StudioMapper();
        studioService = new StudioService(addressService, userService, studioMapper, employeeMapper);

        VALID_STUDIO_NAME = "BeautyS";
        VALID_NIP = "3818483497";
        VALID_REGON = "058205731";
        VALID_PHONE_NUMBER = "123456789";
        VALID_EMAIL = "test@t.x";
        VALID_LOGIN = "xyz";
        VALID_PASSWORD = "GM4l!";
        VALID_EMPLOYEE_NAME = "qwerty";
        VALID_EMPLOYEE_SURNAME = "idk";

        VALID_STUDIO = StudioDto.builder()
                .name(VALID_STUDIO_NAME)
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build();
    }

    @Test
    public void createStudioValidDataShouldPass() {
        //given
        Address address = Address.builder().build();
        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioName("BeautyS")
                .nip("3818483497")
                .regon("058205731")
                .studioPhoneNumber("123456789")
                .studioEmail("test@t.x")
                .address(address)
                .login("xyz")
                .password("GM4l!")
                .employeeName("qwerty")
                .surname("idk")
                .build();

        //when
        CreateStudioResponse createStudioResponse = studioService.createStudioAndAdmin(createStudioRequest);

        //then
        assertEquals(VALID_STUDIO, createStudioResponse.getStudioDto());
    }

    @Test
    public void createStudioNameAsNullShouldThrowError() {
        //given
        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioName(null)
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .studioPhoneNumber(VALID_PHONE_NUMBER)
                .studioEmail(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .employeeName(VALID_EMPLOYEE_NAME)
                .surname(VALID_EMPLOYEE_SURNAME)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> studioService.createStudioAndAdmin(createStudioRequest));
    }

    @Test
    public void createStudioNameAsEmptyStringShouldThrowError() {
        //given
        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioName("")
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .studioPhoneNumber(VALID_PHONE_NUMBER)
                .studioEmail(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .employeeName(VALID_EMPLOYEE_NAME)
                .surname(VALID_EMPLOYEE_SURNAME)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> studioService.createStudioAndAdmin(createStudioRequest));
    }

    @Test
    public void createStudioNameAsStringWithWhiteSpaceShouldThrowError() {
        //given
        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioName(" ")
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .studioPhoneNumber(VALID_PHONE_NUMBER)
                .studioEmail(VALID_EMAIL)
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
                .studioPhoneNumber(VALID_PHONE_NUMBER)
                .studioEmail(VALID_EMAIL)
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
                .studioPhoneNumber(VALID_PHONE_NUMBER)
                .studioEmail(VALID_EMAIL)
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
                .studioPhoneNumber(VALID_PHONE_NUMBER)
                .studioEmail(VALID_EMAIL)
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
                .studioPhoneNumber("1234569")
                .studioEmail(VALID_EMAIL)
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
                .studioPhoneNumber(VALID_PHONE_NUMBER)
                .studioEmail("dsds.pl")
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .employeeName(VALID_EMPLOYEE_NAME)
                .surname(VALID_EMPLOYEE_SURNAME)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> studioService.createStudioAndAdmin(createStudioRequest));
    }

    @Test
    public void createStudioAndAdminCheckIfUserWithAdminPrivilegesWasCreated() {
        //create studio and user
        //given
        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioName(VALID_STUDIO_NAME)
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .studioPhoneNumber(VALID_PHONE_NUMBER)
                .studioEmail(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .employeeName(VALID_EMPLOYEE_NAME)
                .surname(VALID_EMPLOYEE_SURNAME)
                .build();

        //Studio studio = studioService.createStudioAndAdmin(createStudioRequest);

        //when

        //get user by studio id
        //check if user's assigned studio is same as created studio
    }

}