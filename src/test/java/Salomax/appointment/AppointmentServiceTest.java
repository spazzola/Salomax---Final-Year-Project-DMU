package Salomax.appointment;

import Salomax.address.Address;
import Salomax.address.AddressMapper;
import Salomax.appointment.appointmentDetails.AppointmentDetails;
import Salomax.client.Client;
import Salomax.client.ClientDao;
import Salomax.client.ClientMapper;
import Salomax.cost.Cost;
import Salomax.cost.CostService;
import Salomax.employee.Employee;
import Salomax.employee.EmployeeDao;
import Salomax.employee.EmployeeMapper;
import Salomax.employee.WorkRole;
import Salomax.studio.Studio;
import Salomax.studio.StudioDao;
import Salomax.studio.StudioMapper;
import Salomax.validation.ValidationService;
import Salomax.work.Work;
import Salomax.work.WorkDao;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentServiceTest {

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime VALID_START_DATE;
    private Long VALID_STUDIO_ID;
    private Long VALID_CLIENT_ID;
    private Long VALID_EMPLOYEE_ID;
    private List<Long> VALID_WORK_IDS;
    private Appointment VALID_APPOINTMENT;
    private AppointmentService appointmentService;
    @Mock
    private ClientDao clientDao;
    @Mock
    private EmployeeDao employeeDao;
    @Mock
    private StudioDao studioDao;
    @Mock
    private WorkDao workDao;
    @Mock
    private AppointmentDao appointmentDao;


    @Before
    public void setUp() {
        mock(ClientDao.class);
        mock(EmployeeDao.class);
        mock(StudioDao.class);
        mock(WorkDao.class);
        mock(AppointmentDao.class);
        AddressMapper addressMapper = new AddressMapper();
        StudioMapper studioMapper = new StudioMapper(addressMapper);
        ClientMapper clientMapper = new ClientMapper();
        EmployeeMapper employeeMapper = new EmployeeMapper();
        AppointmentMapper appointmentMapper = new AppointmentMapper(studioMapper, clientMapper, employeeMapper);
        appointmentService = new AppointmentService(employeeDao, clientDao, studioDao, workDao, appointmentDao, appointmentMapper);
        VALID_START_DATE = LocalDateTime.now().withSecond(0).withNano(0);
        VALID_STUDIO_ID = 1L;
        VALID_CLIENT_ID = 1L;
        VALID_EMPLOYEE_ID = 1L;
        VALID_WORK_IDS = Arrays.asList(1L, 2L);

        Address address = Address.builder()
                .id(1L)
                .country("xsds")
                .voivodeship("zzz")
                .city("lok")
                .postalCode("zz-zzz")
                .street("lkdsad")
                .houseNumber("00x")
                .build();

        Studio VALID_STUDIO = Studio.builder()
                .id(1L)
                .name("BeautyS")
                .nip("3818483497")
                .regon("058205731")
                .phoneNumber("123456789")
                .email("test@t.x")
                .address(address)
                .build();

        Client VALID_CLIENT = Client.builder()
                .login("abc")
                .password("123!Ac")
                .name("xyz")
                .surname("qwe")
                .phoneNumber("123456789")
                .email("test@x.z")
                .build();

        Employee VALID_EMPLOYEE = Employee.builder()
                .login("abc")
                .password("123!Ac")
                .name("xyz")
                .surname("qwe")
                .phoneNumber("123456789")
                .email("test@x.z")
                .assignedStudio(VALID_STUDIO)
                .workRole(WorkRole.EMPLOYEE)
                .build();

        Work work1 = Work.builder()
                .name("Manicure")
                .price(BigDecimal.valueOf(50))
                .taxValue(BigDecimal.valueOf(20.5))
                .hoursDuration((byte) 1)
                .minutesDuration((byte) 0)
                .iconName("manicure")
                .assignedStudio(VALID_STUDIO)
                .build();
        Work work2 = Work.builder()
                .name("Pedicure")
                .price(BigDecimal.valueOf(25))
                .taxValue(BigDecimal.valueOf(20.5))
                .hoursDuration((byte) 2)
                .minutesDuration((byte) 30)
                .iconName("pedicure")
                .assignedStudio(VALID_STUDIO)
                .build();

        VALID_APPOINTMENT = Appointment.builder()
                .id(1L)
                .price(BigDecimal.valueOf(75))
                .startDate(VALID_START_DATE)
                .endDate(VALID_START_DATE.plusHours(3).plusMinutes(30))
                .assignedStudio(VALID_STUDIO)
                .assignedClient(VALID_CLIENT)
                .assignedEmployee(VALID_EMPLOYEE)
                .build();

        AppointmentDetails appointmentDetails1 = AppointmentDetails.builder()
                .id(1L)
                .appointment(VALID_APPOINTMENT)
                .work(work1)
                .build();

        AppointmentDetails appointmentDetails2 = AppointmentDetails.builder()
                .id(2L)
                .appointment(VALID_APPOINTMENT)
                .work(work2)
                .build();

        List<AppointmentDetails> VALID_APPOINTMENT_DETAILS = Arrays.asList(appointmentDetails1, appointmentDetails2);
        VALID_APPOINTMENT.setAppointmentDetails(VALID_APPOINTMENT_DETAILS);

        when(employeeDao.findById(1L)).thenReturn(Optional.ofNullable(VALID_EMPLOYEE));
        when(clientDao.findById(1L)).thenReturn(Optional.ofNullable(VALID_CLIENT));
        when(studioDao.findById(1L)).thenReturn(Optional.ofNullable(VALID_STUDIO));
        when(workDao.findAllById(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(work1, work2));
        when(appointmentDao.findByStartDateBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(List.of(Appointment.builder().build())));
    }

    @Test
    public void createAppointmentCheckIfEndDateWasCalculatedShouldPass() {
        //given
        CreateAppointmentRequest createAppointmentRequest = CreateAppointmentRequest.builder()
                .startDate(VALID_START_DATE)
                .studioId(VALID_STUDIO_ID)
                .clientId(VALID_CLIENT_ID)
                .employeeId(VALID_EMPLOYEE_ID)
                .workIds(VALID_WORK_IDS)
                .build();

        //when
        CreateAppointmentResponse response = appointmentService.createAppointment(createAppointmentRequest);

        //then
        assertEquals(VALID_APPOINTMENT.getEndDate(), response.getAppointmentDto().getEndDate());
    }

    @Test
    public void createAppointmentCheckIfPriceWasCalculatedShouldPass() {
        //given
        CreateAppointmentRequest createAppointmentRequest = CreateAppointmentRequest.builder()
                .startDate(VALID_START_DATE)
                .studioId(VALID_STUDIO_ID)
                .clientId(VALID_CLIENT_ID)
                .employeeId(VALID_EMPLOYEE_ID)
                .workIds(VALID_WORK_IDS)
                .build();

        //when
        CreateAppointmentResponse response = appointmentService.createAppointment(createAppointmentRequest);

        //then
        assertEquals(BigDecimal.valueOf(75), response.getAppointmentDto().getPrice());
    }

    @Test
    public void createAppointmentCheckIfErrorMessageIsSendWhenStartDateOverlapsShouldPass() {
        //given
        CreateAppointmentRequest createAppointmentRequest = CreateAppointmentRequest.builder()
                .startDate(VALID_START_DATE)
                .studioId(VALID_STUDIO_ID)
                .clientId(VALID_CLIENT_ID)
                .employeeId(VALID_EMPLOYEE_ID)
                .workIds(VALID_WORK_IDS)
                .build();

        //when
        CreateAppointmentResponse response = appointmentService.createAppointment(createAppointmentRequest);

        //then
        assertTrue(response.getMessageException().length() > 0);
    }

}
