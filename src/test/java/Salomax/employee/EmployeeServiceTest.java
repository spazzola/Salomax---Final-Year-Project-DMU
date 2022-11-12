package Salomax.employee;

import Salomax.userDetails.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

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
    private Employee VALID_EMPLOYEE;
    private EmployeeService employeeService;
    @Mock
    private EmployeeDao employeeDao;

    @Before
    public void setUp() {
        UserService userService = new UserService();
        employeeDao = mock(EmployeeDao.class);
        employeeService = new EmployeeService(userService, employeeDao);

        VALID_LOGIN = "abc";
        VALID_PASSWORD = "123";
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
                .build();

        when(employeeDao.save(Mockito.any(Employee.class))).then(returnsFirstArg());
    }


    @Test
    public void createEmployeeValidDataShouldPass() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .login("abc")
                .password("123")
                .name("xyz")
                .surname("qwe")
                .phoneNumber("123456789")
                .email("test@x.z")
                .workRole(WorkRole.EMPLOYEE)
                .build();

        VALID_EMPLOYEE.setWorkRole(WorkRole.EMPLOYEE);

        //when
        Employee employee = employeeService.createEmployee(employeeDto);

        //then
        assertEquals(VALID_EMPLOYEE, employee);
    }

    @Test
    public void createEmployeeInvalidNameShouldThrowError() {
        //given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .name("")
                .password(VALID_PASSWORD)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

}