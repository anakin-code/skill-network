package simplex.bn26.master.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simplex.bn26.master.server.dto.request.CreateCareerRequest;
import simplex.bn26.master.server.dto.request.UpdateCareerRequest;
import simplex.bn26.master.server.dto.response.CareerResponse;
import simplex.bn26.master.server.entity.Career;
import simplex.bn26.master.server.entity.CareerSkill;
import simplex.bn26.master.server.entity.Profile;
import simplex.bn26.master.server.entity.Project;
import simplex.bn26.master.server.entity.Skill;
import simplex.bn26.master.server.repository.CareerRepository;
import simplex.bn26.master.server.repository.CareerSkillRepository;
import simplex.bn26.master.server.repository.ProfileRepository;
import simplex.bn26.master.server.repository.ProjectRepository;
import simplex.bn26.master.server.repository.SkillRepository;

import java.util.List;

@Service
@Transactional
public class CareerService {

    private final CareerRepository careerRepository;
    private final CareerSkillRepository careerSkillRepository;
    private final ProfileRepository profileRepository;
    private final ProjectRepository projectRepository;
    private final SkillRepository skillRepository;

    public CareerService(
            CareerRepository careerRepository,
            CareerSkillRepository careerSkillRepository,
            ProfileRepository profileRepository,
            ProjectRepository projectRepository,
            SkillRepository skillRepository
    ) {
        this.careerRepository = careerRepository;
        this.careerSkillRepository = careerSkillRepository;
        this.profileRepository = profileRepository;
        this.projectRepository = projectRepository;
        this.skillRepository = skillRepository;
    }

    public List<CareerResponse> getCareersByHrid(String hrid) {
        return careerRepository.findByProfileHrid(hrid)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public CareerResponse createCareer(CreateCareerRequest request) {
        Profile profile = profileRepository.findById(request.getHrid())
                .orElseThrow(() -> new IllegalArgumentException("Profile not found: " + request.getHrid()));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + request.getProjectId()));

        Career career = new Career();
        career.setProfile(profile);
        career.setProject(project);
        career.setStartTime(request.getStartTime());
        career.setEndTime(request.getEndTime());

        Career savedCareer = careerRepository.save(career);

        saveCareerSkills(savedCareer, request.getSkillIds());

        return convertToResponseWithSkillIds(savedCareer, request.getSkillIds());
    }

    public CareerResponse updateCareer(Long careerId, UpdateCareerRequest request) {
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new IllegalArgumentException("Career not found: " + careerId));

        if (request.getProjectId() != null) {
            Project project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Project not found: " + request.getProjectId()));
            career.setProject(project);
        }

        if (request.getStartTime() != null) {
            career.setStartTime(request.getStartTime());
        }

        career.setEndTime(request.getEndTime());

        Career savedCareer = careerRepository.save(career);

        if (request.getSkillIds() != null) {
            List<CareerSkill> oldCareerSkills =
                    careerSkillRepository.findByCareerCareerId(careerId);

            careerSkillRepository.deleteAll(oldCareerSkills);
            careerSkillRepository.flush();

            saveCareerSkills(savedCareer, request.getSkillIds());

            return convertToResponseWithSkillIds(savedCareer, request.getSkillIds());
        }

        return convertToResponse(savedCareer);
    }

    public void deleteCareer(Long careerId) {
        if (!careerRepository.existsById(careerId)) {
            throw new IllegalArgumentException("Career not found: " + careerId);
        }

        List<CareerSkill> oldCareerSkills =
                careerSkillRepository.findByCareerCareerId(careerId);

        careerSkillRepository.deleteAll(oldCareerSkills);
        careerSkillRepository.flush();

        careerRepository.deleteById(careerId);
    }

    private void saveCareerSkills(Career career, List<Long> skillIds) {
        if (skillIds == null || skillIds.isEmpty()) {
            return;
        }

        List<CareerSkill> careerSkills = skillIds.stream()
                .distinct()
                .map(skillId -> {
                    Skill skill = skillRepository.findById(skillId)
                            .orElseThrow(() -> new IllegalArgumentException("Skill not found: " + skillId));

                    CareerSkill careerSkill = new CareerSkill();
                    careerSkill.setCareer(career);
                    careerSkill.setSkill(skill);

                    return careerSkill;
                })
                .toList();

        careerSkillRepository.saveAll(careerSkills);
    }

    private CareerResponse convertToResponse(Career career) {
        List<String> skillNames =
                career.getCareerSkills() == null ? List.of() :
                        career.getCareerSkills().stream()
                                .filter(careerSkill -> careerSkill.getSkill() != null)
                                .map(careerSkill -> careerSkill.getSkill().getSkillName())
                                .distinct()
                                .toList();

        return buildCareerResponse(career, skillNames);
    }

    private CareerResponse convertToResponseWithSkillIds(Career career, List<Long> skillIds) {
        List<String> skillNames = skillIds == null ? List.of() :
                skillIds.stream()
                        .distinct()
                        .map(skillId -> skillRepository.findById(skillId)
                                .orElseThrow(() -> new IllegalArgumentException("Skill not found: " + skillId))
                                .getSkillName())
                        .toList();

        return buildCareerResponse(career, skillNames);
    }

    private CareerResponse buildCareerResponse(Career career, List<String> skillNames) {
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
}