package Salomax.studio;

import Salomax.address.Address;
import Salomax.address.AddressDto;
import Salomax.address.AddressService;
import Salomax.employee.EmployeeDto;
import Salomax.employee.EmployeeMapper;
import Salomax.employee.EmployeeService;
import Salomax.employee.WorkRole;
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
    private String VALID_LOGIN;
    private String VALID_PASSWORD;
    private String VALID_EMPLOYEE_NAME;
    private String VALID_EMPLOYEE_SURNAME;
    private StudioDto VALID_STUDIO;
    private EmployeeDto VALID_EMPLOYEE;
    private AddressDto VALID_ADDRESS;
    private StudioService studioService;

    @Before
    public void setUp() {
        UserService userService = new UserService();
        EmployeeService employeeService = new EmployeeService(userService);
        EmployeeMapper employeeMapper = new EmployeeMapper();
        StudioMapper studioMapper = new StudioMapper();
        AddressService addressService = new AddressService(userService);
        studioService = new StudioService(addressService, userService, employeeService, studioMapper, employeeMapper);

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

        VALID_EMPLOYEE = EmployeeDto.builder()
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .name(VALID_EMPLOYEE_NAME)
                .surname(VALID_EMPLOYEE_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build();

        VALID_ADDRESS = AddressDto.builder()
                .country("Ybx")
                .voivodeship("Dbc")
                .city("Jsn")
                .postalCode("00-000")
                .street("uhjj")
                .houseNumber("s3d")
                .build();
    }

    @Test
    public void createStudioValidDataShouldPass() {
        //given
        //Address address = Address.builder().build();
        StudioDto studioDto = StudioDto.builder()
                .name(VALID_STUDIO_NAME)
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build();

        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioDto(studioDto)
                .employeeDto(VALID_EMPLOYEE)
                .addressDto(VALID_ADDRESS)
                .build();

        //when
        CreateStudioResponse createStudioResponse = studioService.createStudioAndAdmin(createStudioRequest);

        //then
        assertEquals(VALID_STUDIO, createStudioResponse.getStudioDto());
    }

    @Test
    public void createStudioNameAsNullShouldThrowError() {
        //given
        StudioDto studioDto = StudioDto.builder()
                .name(null)
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build();

        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioDto(studioDto)
                .employeeDto(VALID_EMPLOYEE)
                .addressDto(VALID_ADDRESS)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> studioService.createStudioAndAdmin(createStudioRequest));
    }

    @Test
    public void createStudioNameAsEmptyStringShouldThrowError() {
        //given
        StudioDto studioDto = StudioDto.builder()
                .name("")
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build();

        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioDto(studioDto)
                .employeeDto(VALID_EMPLOYEE)
                .addressDto(VALID_ADDRESS)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> studioService.createStudioAndAdmin(createStudioRequest));
    }

    @Test
    public void createStudioNameAsStringWithWhiteSpaceShouldThrowError() {
        //given
        StudioDto studioDto = StudioDto.builder()
                .name(" ")
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build();

        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioDto(studioDto)
                .employeeDto(VALID_EMPLOYEE)
                .addressDto(VALID_ADDRESS)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> studioService.createStudioAndAdmin(createStudioRequest));
    }

    @Test
    public void createStudioInvalidNipLengthShouldThrowError() {
        //given
        StudioDto studioDto = StudioDto.builder()
                .name(VALID_STUDIO_NAME)
                .nip("381848349")
                .regon(VALID_REGON)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build();

        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioDto(studioDto)
                .employeeDto(VALID_EMPLOYEE)
                .addressDto(VALID_ADDRESS)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> studioService.createStudioAndAdmin(createStudioRequest));
    }

    @Test
    public void createStudioInvalidNipValueShouldThrowError() {
        //given
        StudioDto studioDto = StudioDto.builder()
                .name(VALID_STUDIO_NAME)
                .nip("3818483496")
                .regon(VALID_REGON)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build();

        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioDto(studioDto)
                .employeeDto(VALID_EMPLOYEE)
                .addressDto(VALID_ADDRESS)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> studioService.createStudioAndAdmin(createStudioRequest));
    }

    @Test
    public void createStudioInvalidRegonValueShouldThrowError() {
        //given
        StudioDto studioDto = StudioDto.builder()
                .name(VALID_STUDIO_NAME)
                .nip(VALID_NIP)
                .regon("058205732")
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build();

        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioDto(studioDto)
                .employeeDto(VALID_EMPLOYEE)
                .addressDto(VALID_ADDRESS)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> studioService.createStudioAndAdmin(createStudioRequest));
    }

    @Test
    public void createStudioTooShortPhoneNumberShouldThrowError() {
        //given
        StudioDto studioDto = StudioDto.builder()
                .name(VALID_STUDIO_NAME)
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .phoneNumber("1234569")
                .email(VALID_EMAIL)
                .build();

        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioDto(studioDto)
                .employeeDto(VALID_EMPLOYEE)
                .addressDto(VALID_ADDRESS)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> studioService.createStudioAndAdmin(createStudioRequest));
    }

    @Test
    public void createStudioWrongEmailShouldThrowError() {
        //given
        StudioDto studioDto = StudioDto.builder()
                .name(VALID_STUDIO_NAME)
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email("dsds.pl")
                .build();

        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioDto(studioDto)
                .employeeDto(VALID_EMPLOYEE)
                .addressDto(VALID_ADDRESS)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> studioService.createStudioAndAdmin(createStudioRequest));
    }

    @Test
    public void createStudioAndAdminCheckIfUserWithAdminPrivilegesWasCreated() {
        //create studio and user
        //given
        StudioDto studioDto = StudioDto.builder()
                .name(VALID_STUDIO_NAME)
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build();

        EmployeeDto adminDto = EmployeeDto.builder()
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .name(VALID_EMPLOYEE_NAME)
                .surname(VALID_EMPLOYEE_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build();

        CreateStudioRequest createStudioRequest = CreateStudioRequest.builder()
                .studioDto(studioDto)
                .employeeDto(adminDto)
                .addressDto(VALID_ADDRESS)
                .build();

        //when
        CreateStudioResponse createStudioResponse = studioService.createStudioAndAdmin(createStudioRequest);

        assertEquals(WorkRole.ADMIN.getRole(), createStudioResponse.getEmployeeDto().getWorkRole());
    }

}