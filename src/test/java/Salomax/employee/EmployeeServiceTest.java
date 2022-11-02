package Salomax.employee;

import Salomax.studio.StudioDto;
import Salomax.userDetails.UserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

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

    @Before
    public void setUp() {
        UserDetailsService userDetailsService = new UserDetailsService();
        employeeService = new EmployeeService(userDetailsService);

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
                .build();

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
                .name(VALID_NAME)
                .surname(VALID_SURNAME)
                .phoneNumber(VALID_PHONE_NUMBER)
                .email(VALID_EMAIL)
                .build();

        //when then
        assertThrows(IllegalArgumentException.class, () -> employeeService.createEmployee(employeeDto));
    }

}