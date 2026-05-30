package server.controller;

import org.springframework.web.bind.annotation.*;
import server.dto.response.PersonalSkillGraphResponse;
import server.dto.response.ProfileResponse;
import server.dto.response.SimilarProfileResponse;
import server.service.ProfileService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{hrid}")
    public ProfileResponse getProfile(@PathVariable String hrid) {
        return profileService.getProfile(hrid);
    }

    @GetMapping("/{hrid}/skill-network")
    public PersonalSkillGraphResponse getPersonalSkillNetwork(@PathVariable String hrid) {
        return profileService.getPersonalSkillNetwork(hrid);
    }

    @GetMapping("/{hrid}/similar")
    public List<SimilarProfileResponse> getSimilarProfiles(
            @PathVariable String hrid,
            @RequestParam(defaultValue = "5") int limit
    ) {
        return profileService.getSimilarProfiles(hrid, limit);
    }
}
