package Salomax.cost;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CostDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private int quantity;
    private double taxValue;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime addedDate;
    private Long assignedStudioId;

}