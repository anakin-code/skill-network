package simplex.bn26.master.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simplex.bn26.master.server.dto.request.UpdateProfileRequest;
import simplex.bn26.master.server.dto.response.ProfileResponse;
import simplex.bn26.master.server.service.ProfileService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProfileResponse>> getAllProfiles() {
        List<ProfileResponse> responses = profileService.getAllProfiles();

        if (responses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(responses);
    }

    @PutMapping("/update/{hrid}")
    public ProfileResponse updateProfile(
            @PathVariable String hrid,
            @RequestBody UpdateProfileRequest request
    ) {
        return profileService.updateProfile(hrid, request);
    }

    @DeleteMapping("/{hrid}")
    public ResponseEntity<Void> deleteProfile(
            @PathVariable String hrid
    ) {
        profileService.deleteProfileByHrid(hrid);
        return ResponseEntity.noContent().build();
    }
}