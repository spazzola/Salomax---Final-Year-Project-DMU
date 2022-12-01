package Salomax.work;

import Salomax.studio.Studio;
import Salomax.studio.StudioDao;
import Salomax.validation.ValidationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Service
public class WorkService {

    private StudioDao studioDao;
    private WorkDao workDao;
    private ValidationService validationService;

    public Work createWork(WorkDto workDto) {
        validate(workDto);

        Studio studio = studioDao.findById(workDto.getAssignedStudioId())
                .orElseThrow();

        Work work = Work.builder()
                .name(workDto.getName())
                .price(workDto.getPrice())
                .taxValue(workDto.getTaxValue())
                .hoursDuration(workDto.getHoursDuration())
                .minutesDuration(workDto.getMinutesDuration())
                .iconName(workDto.getIconName())
                .assignedStudio(studio)
                .build();

        return workDao.save(work);
    }

    public Work updateWork(WorkDto workDto) {
        validate(workDto);

        Work work = workDao.findById(workDto.getId())
                .orElseThrow();

        if (!work.getName().equals(workDto.getName())) {
            work.setName(workDto.getName());
        }
        if (work.getPrice().compareTo(workDto.getPrice()) != 0) {
            work.setPrice(workDto.getPrice());
        }
        if (work.getTaxValue().compareTo(workDto.getTaxValue()) != 0) {
            work.setTaxValue(workDto.getTaxValue());
        }
        if (work.getHoursDuration() != workDto.getHoursDuration()) {
            work.setHoursDuration(workDto.getHoursDuration());
        }
        if (work.getMinutesDuration() != workDto.getMinutesDuration()) {
            work.setMinutesDuration(workDto.getMinutesDuration());
        }
        if (!work.getIconName().equals(workDto.getIconName())) {
            work.setIconName(workDto.getIconName());
        }

        return workDao.save(work);
    }

    public void deleteWork(Long id) {
        workDao.deleteById(id);
    }

    private void validate(WorkDto workDto) {
        String messageException = "";
        if (workDto.getName() == null || !validationService.validateString(workDto.getName())) {
            messageException += "Bad value of work's name. ";
        }
        if (workDto.getPrice() == null || workDto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            messageException += "Price cannot be lower than zero";
        }
        if (workDto.getPrice().compareTo(BigDecimal.valueOf(999999999)) > 0) {
            messageException += "Price is too high. ";
        }
        if (workDto.getMinutesDuration() < 0) {
            messageException += "Bad value of hoursDuration. It cannot be less than zero. ";
        }
        if (workDto.getHoursDuration() > 24) {
            messageException += "Bad value of hoursDuration. It cannot be bigger than 24hrs. ";
        }
        if (workDto.getMinutesDuration() < 0) {
            messageException += "Bad value of minutesDuration. It cannot be less than zero. ";
        }
        if (workDto.getMinutesDuration() > 60) {
            messageException += "Bad value of minutesDuration. It cannot be bigger than 60min. ";
        }
        if (workDto.getIconName() == null || !validationService.validateString(workDto.getIconName())) {
            messageException += "Bad value of iconName. ";
        }

        if (messageException.length() > 1) {
            throw new IllegalArgumentException(messageException);
        }
    }

}