package Salomax.cost;

import Salomax.studio.Studio;
import Salomax.studio.StudioDao;
import Salomax.validation.ValidationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

        Cost cost = buildCost(costDto, studio);

        return costDao.save(cost);
    }

    public List<Cost> createCosts(List<CostDto> costsDto) {
        costsDto.forEach(this::validate);

        Studio studio = studioDao.findById(costsDto.get(0).getAssignedStudioId())
                .orElseThrow();

        List<Cost> costs = costsDto.stream()
                .map(costDto -> buildCost(costDto, studio))
                .collect(Collectors.toList());

        return costDao.saveAll(costs);
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

    public void deleteCost(Long id) {
        costDao.deleteById(id);
    }

    private void validate(CostDto costDto) {
        String messageException = "";
        if (costDto.getAssignedStudioId() == null || costDto.getAssignedStudioId() <= 0) {
            messageException += "Bad value of assignedStudioId. ";
        }
        if (!validationService.validateString(costDto.getName())) {
            messageException += "Bad value of cost's name. ";
        }
        if (costDto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            messageException += "Price cannot be lower than 0. ";
        }
        if (costDto.getQuantity() <= 0) {
            messageException += "Quantity must be greater than 0. ";
        }

        if (messageException.length() > 1) {
            throw new IllegalArgumentException(messageException);
        }
    }

    private Cost buildCost(CostDto costDto, Studio studio) {
        return Cost.builder()
                .name(costDto.getName())
                .price(costDto.getPrice())
                .quantity(costDto.getQuantity())
                .taxValue(costDto.getTaxValue())
                .addedDate(costDto.getAddedDate())
                .assignedStudio(studio)
                .build();
    }

}