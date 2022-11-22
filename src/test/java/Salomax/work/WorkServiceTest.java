package Salomax.work;

import Salomax.client.ClientDao;
import Salomax.studio.Studio;
import Salomax.studio.StudioDao;
import Salomax.validation.ValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WorkServiceTest {

    private String VALID_NAME;
    private BigDecimal VALID_PRICE;
    private BigDecimal VALID_TAX_VALUE;
    private byte VALID_HOURS_DURATION;
    private byte VALID_MINUTES_DURATION;
    private String VALID_ICON_NAME;
    private Work VALID_WORK;
    private WorkService workService;
    @Mock
    private StudioDao studioDao;
    @Mock
    private WorkDao workDao;

    @Before
    public void setUp() {
        ValidationService validationService = new ValidationService();
        mock(StudioDao.class);
        mock(WorkDao.class);
        workService = new WorkService(studioDao, workDao, validationService);

        VALID_NAME = "Manicure";
        VALID_PRICE = BigDecimal.valueOf(70.5);
        VALID_TAX_VALUE = BigDecimal.valueOf(20.5);
        VALID_HOURS_DURATION = 1;
        VALID_MINUTES_DURATION = 30;
        VALID_ICON_NAME = "manicure";

        VALID_WORK = Work.builder()
                .name(VALID_NAME)
                .price(VALID_PRICE)
                .taxValue(VALID_TAX_VALUE)
                .hoursDuration(VALID_HOURS_DURATION)
                .minutesDuration(VALID_MINUTES_DURATION)
                .iconName(VALID_ICON_NAME)
                .build();

        when(studioDao.findById(1L)).thenReturn(Optional.of(Studio.builder()
                .id(1L)
                .build()));
        when(workDao.save(Mockito.any(Work.class))).then(returnsFirstArg());
        when(workDao.findById(1L)).thenReturn(Optional.of(Work.builder()
                .name(VALID_NAME)
                .price(VALID_PRICE)
                .taxValue(VALID_TAX_VALUE)
                .hoursDuration(VALID_HOURS_DURATION)
                .minutesDuration(VALID_MINUTES_DURATION)
                .iconName(VALID_ICON_NAME)
                .build()));
    }

    @Test
    public void createWorkValidDataShouldPass() {
        //given
        WorkDto workDto = WorkDto.builder()
                .name(VALID_NAME)
                .price(VALID_PRICE)
                .taxValue(VALID_TAX_VALUE)
                .hoursDuration(VALID_HOURS_DURATION)
                .minutesDuration(VALID_MINUTES_DURATION)
                .iconName(VALID_ICON_NAME)
                .assignedStudioId(1L)
                .build();

        //when
        Work work = workService.createWork(workDto);

        //then
        assertEquals(VALID_WORK, work);
    }

    @Test
    public void updateWorkCheckIfNameWasUpdated() {
        //given
        WorkDto workDto = WorkDto.builder()
                .id(1L)
                .name("new name")
                .price(VALID_PRICE)
                .hoursDuration(VALID_HOURS_DURATION)
                .minutesDuration(VALID_MINUTES_DURATION)
                .iconName(VALID_ICON_NAME)
                .assignedStudioId(1L)
                .build();

        //when
        Work work = workService.updateWork(workDto);

        //then
        assertEquals("new name", work.getName());
    }

    @Test
    public void updateWorkCheckIfPriceWasUpdated() {
        //given
        WorkDto workDto = WorkDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .price(BigDecimal.valueOf(10.0))
                .hoursDuration(VALID_HOURS_DURATION)
                .minutesDuration(VALID_MINUTES_DURATION)
                .iconName(VALID_ICON_NAME)
                .assignedStudioId(1L)
                .build();

        //when
        Work work = workService.updateWork(workDto);

        //then
        assertEquals(BigDecimal.valueOf(10.0), work.getPrice());
    }

    @Test
    public void updateWorkCheckIfTaxValueWasUpdated() {
        //given
        WorkDto workDto = WorkDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .price(VALID_PRICE)
                .taxValue(BigDecimal.valueOf(9.5))
                .hoursDuration(VALID_HOURS_DURATION)
                .minutesDuration(VALID_MINUTES_DURATION)
                .iconName(VALID_ICON_NAME)
                .assignedStudioId(1L)
                .build();

        //when
        Work work = workService.updateWork(workDto);

        //then
        assertEquals(BigDecimal.valueOf(9.5), work.getTaxValue());
    }

    @Test
    public void updateWorkCheckIfHoursDurationWasUpdated() {
        //given
        WorkDto workDto = WorkDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .price(VALID_PRICE)
                .hoursDuration((byte) 3)
                .minutesDuration(VALID_MINUTES_DURATION)
                .iconName(VALID_ICON_NAME)
                .assignedStudioId(1L)
                .build();

        //when
        Work work = workService.updateWork(workDto);

        //then
        assertEquals((byte) 3, work.getHoursDuration());
    }

    @Test
    public void updateWorkCheckIfMinutesDurationWasUpdated() {
        //given
        WorkDto workDto = WorkDto.builder()
                .id(1L)
                .name("new name")
                .price(VALID_PRICE)
                .hoursDuration(VALID_HOURS_DURATION)
                .minutesDuration((byte) 45)
                .iconName(VALID_ICON_NAME)
                .assignedStudioId(1L)
                .build();

        //when
        Work work = workService.updateWork(workDto);

        //then
        assertEquals((byte) 45, work.getMinutesDuration());
    }

    @Test
    public void updateWorkCheckIfIconNameWasUpdated() {
        //given
        WorkDto workDto = WorkDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .price(VALID_PRICE)
                .hoursDuration(VALID_HOURS_DURATION)
                .minutesDuration(VALID_MINUTES_DURATION)
                .iconName("pedicure")
                .assignedStudioId(1L)
                .build();

        //when
        Work work = workService.updateWork(workDto);

        //then
        assertEquals("pedicure", work.getIconName());
    }

}