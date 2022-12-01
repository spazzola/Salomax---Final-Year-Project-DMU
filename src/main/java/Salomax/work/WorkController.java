package Salomax.work;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/work")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WorkController {

    private WorkService workService;
    private WorkMapper workMapper;

    @PostMapping("/create")
    public WorkDto createWork(@RequestBody WorkDto workDto) {
        Work work = workService.createWork(workDto);
        return workMapper.toDto(work);
    }

    @PutMapping("/update")
    public WorkDto updateWork(@RequestBody WorkDto workDto) {
        Work work = workService.updateWork(workDto);
        return workMapper.toDto(work);
    }

    @DeleteMapping("/delete")
    public HttpStatus deleteWork(@RequestParam("id") Long id) {
        workService.deleteWork(id);
        return HttpStatus.OK;
    }

}