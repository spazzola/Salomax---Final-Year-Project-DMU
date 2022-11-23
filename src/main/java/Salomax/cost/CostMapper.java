package Salomax.cost;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CostMapper {


    public CostDto toDto(Cost cost) {
        return CostDto.builder()
                .id(cost.getId())
                .name(cost.getName())
                .price(cost.getPrice())
                .quantity(cost.getQuantity())
                .taxValue(cost.getTaxValue())
                .addedDate(cost.getAddedDate())
                .assignedStudioId(cost.getAssignedStudio().getId())
                .build();
    }

    public List<CostDto> toDto(List<Cost> costs) {
        return costs.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}