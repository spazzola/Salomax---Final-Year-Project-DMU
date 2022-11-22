package Salomax.work;

import java.util.List;
import java.util.stream.Collectors;

public class WorkMapper {


    public WorkDto toDto(Work work) {
        return WorkDto.builder()
                .id(work.getId())
                .name(work.getName())
                .price(work.getPrice())
                .taxValue(work.getTaxValue())
                .hoursDuration(work.getHoursDuration())
                .minutesDuration(work.getMinutesDuration())
                .iconName(work.getIconName())
                .assignedStudioId(work.getAssignedStudio().getId())
                .build();
    }

    public List<WorkDto> toDto(List<Work> works) {
        return works.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}