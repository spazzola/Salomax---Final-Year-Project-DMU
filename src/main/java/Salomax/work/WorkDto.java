package Salomax.work;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WorkDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private BigDecimal taxValue;
    private byte hoursDuration;
    private byte minutesDuration;
    private String iconName;
    private Long assignedStudioId;

}