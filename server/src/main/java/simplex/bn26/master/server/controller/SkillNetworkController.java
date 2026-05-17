package simplex.bn26.master.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simplex.bn26.master.server.dto.response.ExpertResponse;
import simplex.bn26.master.server.dto.response.PersonalSkillGraphResponse;
import simplex.bn26.master.server.dto.response.SkillGraphResponse;
import simplex.bn26.master.server.service.SkillNetworkService;
import simplex.bn26.master.server.dto.response.SimilarProfileResponse;

import java.util.List;

@RestController
@RequestMapping("/api/skill-network")
@CrossOrigin(origins = "http://localhost:5173")
public class SkillNetworkController {

    private final SkillNetworkService skillNetworkService;

    public SkillNetworkController(SkillNetworkService skillNetworkService) {
        this.skillNetworkService = skillNetworkService;
    }

    @GetMapping("/graph")
    public ResponseEntity<SkillGraphResponse> getSkillGraph() {
        return ResponseEntity.ok(skillNetworkService.getSkillGraph());
    }

    @GetMapping("/experts")
    public ResponseEntity<List<ExpertResponse>> getExperts(
            @RequestParam String skillName
    ) {
        return ResponseEntity.ok(skillNetworkService.getExpertsBySkillName(skillName));
    }

    @GetMapping("/profile/{hrid}")
    public ResponseEntity<PersonalSkillGraphResponse> getPersonalSkillGraph(
            @PathVariable String hrid
    ) {
        return ResponseEntity.ok(skillNetworkService.getPersonalSkillGraph(hrid));
    }

    @GetMapping("/profile/{hrid}/similar")
    public ResponseEntity<List<SimilarProfileResponse>> getSimilarProfiles(
            @PathVariable String hrid,
            @RequestParam(required = false) Integer limit
    ) {
        return ResponseEntity.ok(skillNetworkService.getSimilarProfiles(hrid, limit));
    }
}