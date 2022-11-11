package Salomax.studio;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/studio")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudioController {

    private StudioService studioService;

    @PostMapping("/create")
    public CreateStudioResponse createStudio(@RequestBody CreateStudioRequest createStudioRequest) {
        return studioService.createStudioAndAdmin(createStudioRequest);
    }

}