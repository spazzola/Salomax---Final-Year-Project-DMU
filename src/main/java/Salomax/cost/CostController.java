package Salomax.cost;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/cost")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CostController {

    private CostService costService;
    private CostMapper costMapper;


    @PostMapping("/create")
    public CostDto createCost(@RequestBody CostDto costDto) {
        Cost cost = costService.createCost(costDto);
        return costMapper.toDto(cost);
    }

    @PutMapping("/update")
    public CostDto updateCost(@RequestBody CostDto costDto) {
        Cost cost = costService.updateCost(costDto);
        return costMapper.toDto(cost);
    }

    @DeleteMapping("/delete")
    public HttpStatus deleteCost(@RequestParam("id") Long id) {
        costService.deleteCost(id);
        return HttpStatus.OK;
    }

}