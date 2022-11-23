package Salomax.cost;

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
public class CostService {

    private CostDao costDao;
    private StudioDao studioDao;
    private ValidationService validationService;


    public Cost createCost(CostDto costDto) {
        validate(costDto);

        Studio studio = studioDao.findById(costDto.getAssignedStudioId())
                .orElseThrow();

        Cost cost = Cost.builder()
                .name(costDto.getName())
                .price(costDto.getPrice())
                .quantity(costDto.getQuantity())
                .taxValue(costDto.getTaxValue())
                .addedDate(costDto.getAddedDate())
                .assignedStudio(studio)
                .build();

        return costDao.save(cost);
    }

    public Cost updateCost(CostDto costDto) {
        validate(costDto);

        Cost cost = costDao.findById(costDto.getId())
                .orElseThrow();

        if (!cost.getName().equals(costDto.getName())) {
            cost.setName(costDto.getName());
        }
        if (cost.getPrice().compareTo(costDto.getPrice()) != 0) {
            cost.setPrice(costDto.getPrice());
        }
        if (cost.getQuantity() != costDto.getQuantity()) {
            cost.setQuantity(costDto.getQuantity());
        }
        if (cost.getTaxValue() != costDto.getTaxValue()) {
            cost.setTaxValue(costDto.getTaxValue());
        }
        if (!cost.getAddedDate().isEqual(costDto.getAddedDate())) {
            cost.setAddedDate(costDto.getAddedDate());
        }

        return costDao.save(cost);
    }

    private void validate(CostDto costDto) {
        String messageException = "";
        if (!validationService.validateString(costDto.getName())) {
            messageException += "Bad value of cost's name. ";
        }
        if (costDto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            messageException += "Price cannot be lower than 0. ";
        }
        if (costDto.getQuantity() <= 0) {
            messageException += "Quantity must be greater than 0. ";
        }
    }

}