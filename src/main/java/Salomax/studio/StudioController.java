package Salomax.studio;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/studio")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudioController {

    private StudioService studioService;
    private StudioMapper studioMapper;

    @PostMapping("/create")
    public CreateStudioResponse createStudio(@RequestBody CreateStudioRequest createStudioRequest) {
        return studioService.createStudioAndAdmin(createStudioRequest);
    }

    @PutMapping("/update")
    public StudioDto updateStudio(@RequestBody StudioDto studioDto) {
        Studio studio = studioService.updateStudio(studioDto);
        return studioMapper.toDto(studio);
    }

}