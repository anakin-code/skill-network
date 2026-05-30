package server.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.dto.request.CreateSkillRequest;
import server.dto.response.ExpertResponse;
import server.dto.response.SkillNetworkResponse;
import server.dto.response.SkillResponse;
import server.service.SkillService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/skills")
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public List<SkillResponse> getSkillAll() {
        return skillService.getSkillAll();
    }

    @GetMapping("/search")
    public List<SkillResponse> searchSkills(@RequestParam(required = false) String keyword) {
        return skillService.searchSkills(keyword);
    }

    @GetMapping("/network")
    public ResponseEntity<SkillNetworkResponse> getSkillNetwork() {
        return ResponseEntity.ok(skillService.getSkillNetwork());
    }

    @GetMapping("/{skillName}/experts")
    public List<ExpertResponse> getExperts(@PathVariable String skillName) {
        return skillService.getExperts(skillName);
    }

    @PostMapping
    public SkillResponse createSkill(@Valid @RequestBody CreateSkillRequest request) {
        return skillService.createSkill(request);
    }

    @DeleteMapping("/{skillId}")
    public void deleteSkill(@PathVariable Long skillId) {
        skillService.deleteSkill(skillId);
    }
}
