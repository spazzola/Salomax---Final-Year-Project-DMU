package Salomax.studio;

import Salomax.address.AddressDao;
import Salomax.address.AddressDto;
import Salomax.address.AddressMapper;
import Salomax.address.AddressService;
import Salomax.employee.*;
import Salomax.userDetails.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    private StudioDto VALID_STUDIO_DTO;
    private EmployeeDto VALID_EMPLOYEE_DTO;
    private AddressDto VALID_ADDRESS_DTO;
    private StudioService studioService;
    @Mock
    private StudioDao studioDao;
    @Mock
    private AddressDao addressDao;
    @Mock
    private EmployeeDao employeeDao;

    @Before
    public void setUp() {
        studioDao = mock(StudioDao.class);
        addressDao = mock(AddressDao.class);
        employeeDao = mock(EmployeeDao.class);
        UserService userService = new UserService();
        EmployeeService employeeService = new EmployeeService(userService, employeeDao);
        EmployeeMapper employeeMapper = new EmployeeMapper();
        AddressMapper addressMapper = new AddressMapper();
        StudioMapper studioMapper = new StudioMapper(addressMapper);
        AddressService addressService = new AddressService(userService, addressDao);
        studioService = new StudioService(studioDao, addressService, userService, employeeService, studioMapper, employeeMapper);

        VALID_STUDIO_NAME = "BeautyS";
        VALID_NIP = "3818483497";
        VALID_REGON = "058205731";
        VALID_PHONE_NUMBER = "123456789";
        VALID_EMAIL = "test@t.x";
        VALID_LOGIN = "xyz";
        VALID_PASSWORD = "GM4l!";
        VALID_EMPLOYEE_NAME = "qwerty";
        VALID_EMPLOYEE_SURNAME = "idk";

        VALID_STUDIO_DTO = StudioDto.builder()
                .name(VALID_STUDIO_NAME)
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build();

        VALID_EMPLOYEE_DTO = EmployeeDto.builder()
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .name(VALID_EMPLOYEE_NAME)
                .surname(VALID_EMPLOYEE_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build();

        VALID_ADDRESS_DTO = AddressDto.builder()
                .country("Ybx")
                .voivodeship("Dbc")
                .city("Jsn")
                .postalCode("00-000")
                .street("uhjj")
                .houseNumber("s3d")
                .build();

        Studio studio = Studio.builder().build();
        when(studioDao.findById(1L)).thenReturn(Optional.of(Studio.builder()
                        .name(VALID_STUDIO_NAME)
                        .nip(VALID_NIP)
                        .regon(VALID_REGON)
                        .phoneNumber(VALID_PHONE_NUMBER)
                        .email(VALID_EMAIL)
                        .build()));
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
                .employeeDto(VALID_EMPLOYEE_DTO)
                //.addressDto(VALID_ADDRESS_DTO)
                .build();

        //when
        CreateStudioResponse createStudioResponse = studioService.createStudioAndAdmin(createStudioRequest);

        //then
        assertEquals(VALID_STUDIO_DTO, createStudioResponse.getStudioDto());
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
                .employeeDto(VALID_EMPLOYEE_DTO)
                //.addressDto(VALID_ADDRESS_DTO)
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
                .employeeDto(VALID_EMPLOYEE_DTO)
                //.addressDto(VALID_ADDRESS_DTO)
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
                .employeeDto(VALID_EMPLOYEE_DTO)
                //.addressDto(VALID_ADDRESS_DTO)
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
                .employeeDto(VALID_EMPLOYEE_DTO)
                //.addressDto(VALID_ADDRESS_DTO)
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
                .employeeDto(VALID_EMPLOYEE_DTO)
                //.addressDto(VALID_ADDRESS_DTO)
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
                .employeeDto(VALID_EMPLOYEE_DTO)
                //.addressDto(VALID_ADDRESS_DTO)
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
                .employeeDto(VALID_EMPLOYEE_DTO)
                //.addressDto(VALID_ADDRESS_DTO)
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
                .employeeDto(VALID_EMPLOYEE_DTO)
                //.addressDto(VALID_ADDRESS_DTO)
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
                //.addressDto(VALID_ADDRESS_DTO)
                .build();

        //when
        CreateStudioResponse createStudioResponse = studioService.createStudioAndAdmin(createStudioRequest);

        assertEquals(WorkRole.ADMIN.getRole(), createStudioResponse.getEmployeeDto().getWorkRole());
    }

    @Test
    public void updateStudioCheckIfNameWasUpdated() {
        //given
        StudioDto studioDto = StudioDto.builder()
                .id(1L)
                .name("new name")
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build();

        //when
        Studio studio = studioService.updateStudio(studioDto);

        //then
        assertEquals("new name", studio.getName());
    }

    @Test
    public void updateStudioCheckIfNipWasUpdated() {
        //given
        StudioDto studioDto = StudioDto.builder()
                .id(1L)
                .name(VALID_STUDIO_NAME)
                .nip("1564710668")
                .regon(VALID_REGON)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build();

        //when
        Studio studio = studioService.updateStudio(studioDto);

        //then
        assertEquals("1564710668", studio.getNip());
    }

    @Test
    public void updateStudioCheckIfRegonWasUpdated() {
        //given
        StudioDto studioDto = StudioDto.builder()
                .id(1L)
                .name(VALID_STUDIO_NAME)
                .nip(VALID_NIP)
                .regon("092533555")
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build();

        //when
        Studio studio = studioService.updateStudio(studioDto);

        //then
        assertEquals("092533555", studio.getRegon());
    }

    @Test
    public void updateStudioCheckIfPhoneNumberWasUpdated() {
        //given
        StudioDto studioDto = StudioDto.builder()
                .id(1L)
                .name(VALID_STUDIO_NAME)
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .phoneNumber("111222333")
                .email(VALID_EMAIL)
                .build();

        //when
        Studio studio = studioService.updateStudio(studioDto);

        //then
        assertEquals("111222333", studio.getPhoneNumber());
    }

    @Test
    public void updateStudioCheckIfMailWasUpdated() {
        //given
        StudioDto studioDto = StudioDto.builder()
                .id(1L)
                .name(VALID_STUDIO_NAME)
                .nip(VALID_NIP)
                .regon(VALID_REGON)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email("p@x.p")
                .build();

        //when
        Studio studio = studioService.updateStudio(studioDto);

        //then
        assertEquals("p@x.p", studio.getEmail());
    }

}