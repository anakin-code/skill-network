package skill.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skill.server.dto.request.CreateCareerRequest;
import skill.server.dto.request.UpdateCareerRequest;
import skill.server.dto.response.CareerResponse;
import skill.server.service.CareerService;

import java.util.List;

@RestController
@RequestMapping("/api/career")
@CrossOrigin(origins = "http://localhost:5173")
public class CareerController {

    private final CareerService careerService;

    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @GetMapping("/profile/{hrid}")
    public ResponseEntity<List<CareerResponse>> getCareersByProfile(
            @PathVariable String hrid
    ) {
        return ResponseEntity.ok(careerService.getCareersByHrid(hrid));
    }

    @PostMapping("/create")
    public ResponseEntity<CareerResponse> createCareer(
            @RequestBody CreateCareerRequest request
    ) {
        return ResponseEntity.ok(careerService.createCareer(request));
    }

    @PutMapping("/{careerId}")
    public ResponseEntity<CareerResponse> updateCareer(
            @PathVariable Long careerId,
            @RequestBody UpdateCareerRequest request
    ) {
        return ResponseEntity.ok(careerService.updateCareer(careerId, request));
    }

    @DeleteMapping("/{careerId}")
    public ResponseEntity<Void> deleteCareer(
            @PathVariable Long careerId
    ) {
        careerService.deleteCareer(careerId);
        return ResponseEntity.noContent().build();
    }
}