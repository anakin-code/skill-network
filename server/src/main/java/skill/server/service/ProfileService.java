package skill.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skill.server.dto.request.UpdateProfileRequest;
import skill.server.dto.response.CareerResponse;
import skill.server.dto.response.ProfileResponse;
import skill.server.entity.Career;
import skill.server.entity.Profile;
import skill.server.repository.ProfileRepository;

import java.util.List;

@Service
@Transactional
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> getProfilesByRank(String rank) {
        return profileRepository.findByRankRankName(rank);
    }

    public List<Profile> getProfilesByHrid(String hrid) {
        return profileRepository.findByHridContaining(hrid);
    }

    private List<ProfileResponse> getProfileResponseList(List<Profile> profiles) {
        return profiles.stream()
                .map(this::convertToProfileResponse)
                .toList();
    }

    private ProfileResponse convertToProfileResponse(Profile profile) {
        List<CareerResponse> careerResponses =
                profile.getCareers() == null ? List.of() :
                        profile.getCareers().stream()
                                .map(this::convertToCareerResponse)
                                .toList();

        return new ProfileResponse(
                profile.getHrid(),
                profile.getName(),
                profile.getMailAddress(),
                profile.getRank() != null ? profile.getRank().getRankName() : null,
                profile.getFree(),
                careerResponses
        );
    }

    private CareerResponse convertToCareerResponse(Career career) {
        List<String> skillNames =
                career.getCareerSkills() == null ? List.of() :
                        career.getCareerSkills().stream()
                                .filter(careerSkill -> careerSkill.getSkill() != null)
                                .map(careerSkill -> careerSkill.getSkill().getSkillName())
                                .toList();

        return new CareerResponse(
                career.getCareerId(),
                career.getProject() != null ? career.getProject().getProjectId() : null,
                career.getProject() != null ? career.getProject().getProjectContent() : null,
                career.getProject() != null ? career.getProject().getProjectName() : null,
                career.getStartTime(),
                career.getEndTime(),
                skillNames
        );
    }

    public List<ProfileResponse> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        return getProfileResponseList(profiles);
    }

    @Transactional
    public void deleteProfileByHrid(String hrid) {
        if (!profileRepository.existsById(hrid)) {
            throw new IllegalArgumentException("Profile not found");
        }

        profileRepository.deleteById(hrid);
    }

    public String getDivisionName(Profile profile) {
        if (profile.getCareers() != null && !profile.getCareers().isEmpty()) {
            Career firstCareer = profile.getCareers().get(0);

            if (firstCareer != null
                    && firstCareer.getProject() != null
                    && firstCareer.getProject().getDivision() != null) {
                return firstCareer.getProject().getDivision().getDivisionName();
            }
        }

        return "No division";
    }

    public ProfileResponse updateProfile(String hrid, UpdateProfileRequest request) {
        Profile profile = profileRepository.findById(hrid)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        profile.setFree(request.getFree());

        Profile savedProfile = profileRepository.save(profile);

        return convertToProfileResponse(savedProfile);
    }

    public List<ProfileResponse> getProfilesByMailAddress(String mailAddress) {
        return getProfileResponseList(profileRepository.findByMailAddressContaining(mailAddress));
    }

    public List<ProfileResponse> getProfilesByName(String name) {
        return getProfileResponseList(profileRepository.findByNameContaining(name));
    }
}