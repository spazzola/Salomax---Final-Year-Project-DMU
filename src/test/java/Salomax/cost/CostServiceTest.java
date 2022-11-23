package Salomax.cost;

import Salomax.studio.Studio;
import Salomax.studio.StudioDao;
import Salomax.validation.ValidationService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CostServiceTest {

    private String VALID_NAME;
    private BigDecimal VALID_PRICE;
    private int VALID_QUANTITY;
    private double VALID_TAX_VALUE;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime VALID_ADDED_DATE;
    private Cost VALID_COST;
    private CostService costService;
    @Mock
    private CostDao costDao;
    @Mock
    private StudioDao studioDao;


    @Before
    public void setUp() {
        ValidationService validationService = new ValidationService();
        costService = new CostService(costDao, studioDao, validationService);

        VALID_NAME = "water";
        VALID_PRICE = BigDecimal.valueOf(11.5);
        VALID_QUANTITY = 2;
        VALID_TAX_VALUE = 20.5;
        VALID_ADDED_DATE = LocalDateTime.of(2022, Month.FEBRUARY, 22, 0, 0);

        VALID_COST = Cost.builder()
                .name(VALID_NAME)
                .price(VALID_PRICE)
                .quantity(VALID_QUANTITY)
                .taxValue(VALID_TAX_VALUE)
                .addedDate(VALID_ADDED_DATE)
                .build();

        when(studioDao.findById(1L)).thenReturn(Optional.of(Studio.builder()
                .id(1L)
                .build()));
        when(costDao.findById(1L)).thenReturn(Optional.of(Cost.builder()
                .id(1L)
                .name(VALID_NAME)
                .price(VALID_PRICE)
                .quantity(VALID_QUANTITY)
                .taxValue(VALID_TAX_VALUE)
                .addedDate(VALID_ADDED_DATE)
                .build()));
        when(costDao.save(Mockito.any(Cost.class))).then(returnsFirstArg());
    }


    @Test
    public void createCostValidDataShouldPass() {
        //given
        CostDto costDto = CostDto.builder()
                .name(VALID_NAME)
                .price(VALID_PRICE)
                .quantity(VALID_QUANTITY)
                .taxValue(VALID_TAX_VALUE)
                .addedDate(VALID_ADDED_DATE)
                .assignedStudioId(1L)
                .build();

        //when
        Cost cost = costService.createCost(costDto);

        //then
        assertEquals(VALID_COST, cost);
    }

    @Test
    public void updateCostCheckIfNameWasUpdated() {
        //given
        CostDto costDto = CostDto.builder()
                .id(1L)
                .name("new name")
                .price(VALID_PRICE)
                .quantity(VALID_QUANTITY)
                .taxValue(VALID_TAX_VALUE)
                .addedDate(VALID_ADDED_DATE)
                .assignedStudioId(1L)
                .build();

        //when
        Cost cost = costService.updateCost(costDto);

        //then
        assertEquals("new name", cost.getName());
    }

    @Test
    public void updateCostCheckIfPriceWasUpdated() {
        //given
        CostDto costDto = CostDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .price(BigDecimal.valueOf(99.3))
                .quantity(VALID_QUANTITY)
                .taxValue(VALID_TAX_VALUE)
                .addedDate(VALID_ADDED_DATE)
                .assignedStudioId(1L)
                .build();

        //when
        Cost cost = costService.updateCost(costDto);

        //then
        assertEquals(BigDecimal.valueOf(99.3), cost.getPrice());
    }

    @Test
    public void updateCostCheckIfQuantityWasUpdated() {
        //given
        CostDto costDto = CostDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .price(VALID_PRICE)
                .quantity(99)
                .taxValue(VALID_TAX_VALUE)
                .addedDate(VALID_ADDED_DATE)
                .assignedStudioId(1L)
                .build();

        //when
        Cost cost = costService.updateCost(costDto);

        //then
        assertEquals(99, cost.getQuantity());
    }

    @Test
    public void updateCostCheckIfTaxValueWasUpdated() {
        //given
        CostDto costDto = CostDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .price(VALID_PRICE)
                .quantity(VALID_QUANTITY)
                .taxValue(15.3)
                .addedDate(VALID_ADDED_DATE)
                .assignedStudioId(1L)
                .build();

        //when
        Cost cost = costService.updateCost(costDto);

        //then
        assertEquals(15.3, cost.getTaxValue());
    }

    @Test
    public void updateCostCheckIfTAddedDateWasUpdated() {
        //given
        CostDto costDto = CostDto.builder()
                .id(1L)
                .name(VALID_NAME)
                .price(VALID_PRICE)
                .quantity(VALID_QUANTITY)
                .taxValue(VALID_TAX_VALUE)
                .addedDate(LocalDateTime.of(2023, Month.JANUARY, 12, 0, 0))
                .assignedStudioId(1L)
                .build();

        //when
        Cost cost = costService.updateCost(costDto);

        //then
        assertEquals(LocalDateTime.of(2023, Month.JANUARY, 12, 0, 0), cost.getAddedDate());
    }

}
