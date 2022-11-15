package Salomax.employee;

import Salomax.studio.Studio;
import Salomax.studio.StudioDao;
import Salomax.user.User;
import Salomax.validation.ValidationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmployeeServiceTest {

    private String VALID_LOGIN;
    private String VALID_PASSWORD;
    private String VALID_NAME;
    private String VALID_SURNAME;
    private String VALID_PHONE_NUMBER;
    private String VALID_EMAIL;
    private User VALID_EMPLOYEE;
    private EmployeeService employeeService;
    @Mock
    private EmployeeDao employeeDao;
    @Mock
    private StudioDao studioDao;

    @Before
    public void setUp() {
        ValidationService validationService = new ValidationService();
        employeeDao = mock(EmployeeDao.class);
        studioDao = mock(StudioDao.class);
        employeeService = new EmployeeService(validationService, employeeDao, studioDao);

        VALID_LOGIN = "abc";
        VALID_PASSWORD = "123!Ac";
        VALID_NAME = "xyz";
        VALID_SURNAME = "qwe";
        VALID_PHONE_NUMBER = "123456789";
        VALID_EMAIL = "test@x.z";

        VALID_EMPLOYEE = Employee.builder()
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .workRole(WorkRole.EMPLOYEE)
                .build();

        when(employeeDao.save(Mockito.any(Employee.class))).then(returnsFirstArg());
        when(studioDao.findById(1L)).thenReturn(Optional.of(new Studio()));
    }


    @Test
    public void createEmployeeValidDataShouldPass() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .workRole(WorkRole.EMPLOYEE)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .assignedStudioId(1L)
                .build();

        //when
        User employee = employeeService.createEmployee(employeeDto);

        //then
        assertEquals(VALID_EMPLOYEE, employee);
    }

    @Test
    public void createEmployeeNameAsNullShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name(null)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    public void createEmployeeNameAsEmptyStringShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name("")
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    public void createEmployeeNameAsEmptyStringWithWhiteSpaceShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name(" ")
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    public void createEmployeeSurnameAsNullShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name(VALID_NAME)
                .surname(null)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    public void createEmployeeSurnameAsEmptyStringShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name(VALID_NAME)
                .surname("")
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    public void createEmployeeSurnameAsEmptyStringWithWhiteSpaceShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name(VALID_NAME)
                .surname(" ")
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    public void createEmployeePhoneNumberAsNullShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(null)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    public void createEmployeeTooShortPhoneNumberShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber("12345678")
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    public void createEmployeeTooLongPhoneNumberShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber("123456789012345")
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    public void createEmployeeWrongEmailShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email("x.px")
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    public void createEmployeeLoginAsNullShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login(null)
                .password(VALID_PASSWORD)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    public void createEmployeeLoginEmptyStringShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login("")
                .password(VALID_PASSWORD)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    public void createEmployeeLoginEmptyStringWithWhiteSpaceShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login(" ")
                .password(VALID_PASSWORD)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    public void createEmployeePasswordAsNullShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(null)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    public void createEmployeeTooShortPasswordShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password("x!1C")
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    public void createEmployeePasswordWithoutDigitShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password("x!xhcbCsc")
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    public void createEmployeePasswordWithoutSpecialCharacterShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password("x1xhcbDsc")
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    public void createEmployeePasswordWithoutUpperCaseShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password("x!xhcbhsc1")
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

    @Test
    public void createEmployeePasswordWithoutLowerCaseShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password("!1CCCCCC")
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

}