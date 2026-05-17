package simplex.bn26.master.server.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import simplex.bn26.master.server.dto.response.SkillResponse;
import simplex.bn26.master.server.entity.Skill;
import simplex.bn26.master.server.service.SkillService;

import java.util.List;

@RestController
@RequestMapping("/api/skill")
public class SkillController {

    private final SkillService skillService;

    public SkillController(
            SkillService skillService
    ) {
        this.skillService = skillService;
    }

    @GetMapping("/all")
    public List<SkillResponse> getSkillAll() {
        return skillService.getSkillAll();
    }

    @GetMapping("/search")
    public List<SkillResponse> searchSkill(
            @RequestParam String keyword
    ) {
        return skillService.searchSkill(keyword);
    }

    @PostMapping("/create")
    public Skill createSkill(
            @Valid @RequestBody Skill skill
    ) {
        return skillService.createSkill(skill);
    }

    @GetMapping("/{id}")
    public SkillResponse getSkill(
            @PathVariable Long id
    ) {
        return skillService.getSkill(id);
    }

    @PutMapping("/{id}")
    public SkillResponse putSkill(
            @PathVariable Long id,
            @Valid @RequestBody Skill skill
    ) {
        Skill updateSkill = skillService.postSkill(id, skill);

        return new SkillResponse(
                updateSkill.getSkillId(),
                updateSkill.getSkillName()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteSkill(
            @PathVariable Long id
    ) {
        skillService.deleteSkill(id);
    }
}