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
        when(employeeDao.findById(1L)).thenReturn(Optional.of(Employee.builder()
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .workRole(WorkRole.EMPLOYEE)
                .note("")
                .build()));
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
    public void updateEmployeeCheckIfNameWasUpdated() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(1L)
                .name("new name")
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .workRole(WorkRole.EMPLOYEE)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .note("")
                .assignedStudioId(1L)
                .build();

        //when
        Employee employee = employeeService.updateEmployee(employeeDto);

        //then
        assertEquals("new name", employee.getName());
    }

    @Test
    public void updateEmployeeCheckIfSurnameWasUpdated() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .surname("new surname")
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .workRole(WorkRole.EMPLOYEE)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .note("")
                .assignedStudioId(1L)
                .build();

        //when
        Employee employee = employeeService.updateEmployee(employeeDto);

        //then
        assertEquals("new surname", employee.getSurname());
    }

    @Test
    public void updateEmployeeCheckIfPhoneNumberWasUpdated() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber("444444444")
                .email(VALID_EMAIL)
                .workRole(WorkRole.EMPLOYEE)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .note("")
                .assignedStudioId(1L)
                .build();

        //when
        Employee employee = employeeService.updateEmployee(employeeDto);

        //then
        assertEquals("444444444", employee.getPhoneNumber());
    }

    @Test
    public void updateEmployeeCheckIfMailWasUpdated() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email("newmail@x.l")
                .workRole(WorkRole.EMPLOYEE)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .note("")
                .assignedStudioId(1L)
                .build();

        //when
        Employee employee = employeeService.updateEmployee(employeeDto);

        //then
        assertEquals("newmail@x.l", employee.getEmail());
    }

    @Test
    public void updateEmployeeCheckIfWorkRoleWasUpdated() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .workRole(WorkRole.ADMIN)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .note("")
                .assignedStudioId(1L)
                .build();

        //when
        Employee employee = employeeService.updateEmployee(employeeDto);

        //then
        assertEquals(WorkRole.ADMIN, employee.getWorkRole());
    }

    @Test
    public void updateEmployeeCheckIfNoteWasUpdated() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .workRole(WorkRole.EMPLOYEE)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .note("new note")
                .assignedStudioId(1L)
                .build();

        //when
        Employee employee = employeeService.updateEmployee(employeeDto);

        //then
        assertEquals("new note", employee.getNote());
    }

    @Test
    public void updateEmployeeCheckIfLoginWasUpdated() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .workRole(WorkRole.EMPLOYEE)
                .login("newLogin")
                .password(VALID_PASSWORD)
                .note("")
                .assignedStudioId(1L)
                .build();

        //when
        Employee employee = employeeService.updateEmployee(employeeDto);

        //then
        assertEquals("newLogin", employee.getLogin());
    }

    @Test
    public void updateEmployeeCheckIfPasswordWasUpdated() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .workRole(WorkRole.EMPLOYEE)
                .login(VALID_LOGIN)
                .password("newPassword1!")
                .note("")
                .assignedStudioId(1L)
                .build();

        //when
        Employee employee = employeeService.updateEmployee(employeeDto);

        //then
        assertEquals("newPassword1!", employee.getPassword());
    }

}