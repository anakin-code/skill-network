package server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.dto.response.*;
import server.entity.*;
import simplex.bn26.master.server.dto.response.*;
import simplex.bn26.master.server.entity.*;
import server.repository.CareerRepository;
import server.repository.CareerSkillRepository;
import server.repository.ProfileRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final CareerRepository careerRepository;
    private final CareerSkillRepository careerSkillRepository;

    public ProfileService(
            ProfileRepository profileRepository,
            CareerRepository careerRepository,
            CareerSkillRepository careerSkillRepository
    ) {
        this.profileRepository = profileRepository;
        this.careerRepository = careerRepository;
        this.careerSkillRepository = careerSkillRepository;
    }

    public ProfileResponse getProfile(String hrid) {
        Profile profile = profileRepository.findById(hrid)
                .orElseThrow(() -> new IllegalArgumentException("社員が見つかりません: " + hrid));

        List<Career> careers = careerRepository.findByProfileHrid(hrid);
        List<CareerResponse> careerResponses = careers.stream()
                .map(this::toCareerResponse)
                .toList();

        List<SkillExperienceResponse> skillExperiences = toSkillExperiences(careers);

        return new ProfileResponse(
                profile.getHrid(),
                profile.getName(),
                profile.getMailAddress(),
                profile.getFree(),
                profile.getRank() == null ? null : profile.getRank().getRankName(),
                careerResponses,
                skillExperiences
        );
    }

    public PersonalSkillGraphResponse getPersonalSkillNetwork(String hrid) {
        List<Career> careers = careerRepository.findByProfileHrid(hrid);
        if (careers.isEmpty()) {
            profileRepository.findById(hrid)
                    .orElseThrow(() -> new IllegalArgumentException("社員が見つかりません: " + hrid));
        }

        List<CareerSkill> careerSkills = getCareerSkills(careers);

        Map<Long, String> skillNameMap = new HashMap<>();
        Map<Long, Integer> totalMonthsMap = new HashMap<>();
        Map<String, Integer> edgeMonthsMap = new HashMap<>();

        Map<Long, List<CareerSkill>> byCareerId = careerSkills.stream()
                .collect(Collectors.groupingBy(cs -> cs.getCareer().getCareerId()));

        for (CareerSkill cs : careerSkills) {
            Long skillId = cs.getSkill().getSkillId();
            skillNameMap.put(skillId, cs.getSkill().getSkillName());
            totalMonthsMap.merge(skillId, months(cs), Integer::sum);
        }

        for (List<CareerSkill> list : byCareerId.values()) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    CareerSkill a = list.get(i);
                    CareerSkill b = list.get(j);
                    Long idA = a.getSkill().getSkillId();
                    Long idB = b.getSkill().getSkillId();
                    if (idA.equals(idB)) continue;

                    Long source = Math.min(idA, idB);
                    Long target = Math.max(idA, idB);
                    int coMonths = Math.min(months(a), months(b));
                    edgeMonthsMap.merge(source + "-" + target, coMonths, Integer::sum);
                }
            }
        }

        int maxMonths = totalMonthsMap.values().stream().max(Integer::compareTo).orElse(1);

        List<PersonalSkillNodeResponse> nodes = totalMonthsMap.entrySet().stream()
                .map(e -> new PersonalSkillNodeResponse(
                        e.getKey(),
                        skillNameMap.get(e.getKey()),
                        e.getValue(),
                        maxMonths == 0 ? 0.0 : (double) e.getValue() / maxMonths
                ))
                .toList();

        List<PersonalSkillEdgeResponse> edges = edgeMonthsMap.entrySet().stream()
                .map(e -> {
                    String[] ids = e.getKey().split("-");
                    Long source = Long.valueOf(ids[0]);
                    Long target = Long.valueOf(ids[1]);
                    int coMonths = e.getValue();
                    int denominator = Math.min(
                            totalMonthsMap.getOrDefault(source, 0),
                            totalMonthsMap.getOrDefault(target, 0)
                    );
                    double normalizedWeight = denominator == 0 ? 0.0 : (double) coMonths / denominator;
                    return new PersonalSkillEdgeResponse(source, target, coMonths, normalizedWeight);
                })
                .toList();

        return new PersonalSkillGraphResponse(nodes, edges);
    }

    public List<SimilarProfileResponse> getSimilarProfiles(String hrid, int limit) {
        Profile target = profileRepository.findById(hrid)
                .orElseThrow(() -> new IllegalArgumentException("社員が見つかりません: " + hrid));

        Map<Long, Integer> targetSkillMonths = skillMonthsByProfile(hrid);
        Set<Long> targetSkillIds = targetSkillMonths.keySet();

        return profileRepository.findAll().stream()
                .filter(profile -> !profile.getHrid().equals(target.getHrid()))
                .map(profile -> {
                    Map<Long, Integer> otherSkillMonths = skillMonthsByProfile(profile.getHrid());
                    Set<Long> commonSkillIds = new HashSet<>(targetSkillIds);
                    commonSkillIds.retainAll(otherSkillMonths.keySet());

                    double similarity = cosineSimilarity(targetSkillMonths, otherSkillMonths);
                    String commonSkillNames = commonSkillNames(commonSkillIds);

                    return new SimilarProfileResponse(
                            profile.getHrid(),
                            profile.getName(),
                            similarity,
                            commonSkillNames
                    );
                })
                .filter(p -> p.getSimilarity() > 0)
                .sorted(Comparator.comparingDouble(SimilarProfileResponse::getSimilarity).reversed())
                .limit(limit)
                .toList();
    }

    private CareerResponse toCareerResponse(Career career) {
        Project project = career.getProject();
        Division division = project == null ? null : project.getDivision();
        Company company = division == null ? null : division.getCompany();

        List<String> skillNames = careerSkillRepository.findByCareerCareerId(career.getCareerId()).stream()
                .map(cs -> cs.getSkill().getSkillName())
                .toList();

        return new CareerResponse(
                career.getCareerId(),
                project == null ? null : project.getProjectId(),
                project == null ? null : project.getProjectName(),
                project == null ? null : project.getProjectContent(),
                division == null ? null : division.getDivisionName(),
                company == null ? null : company.getCompanyName(),
                career.getStartTime(),
                career.getEndTime(),
                skillNames
        );
    }

    private List<SkillExperienceResponse> toSkillExperiences(List<Career> careers) {
        List<CareerSkill> careerSkills = getCareerSkills(careers);
        Map<Long, String> nameMap = new HashMap<>();
        Map<Long, Integer> monthMap = new HashMap<>();

        for (CareerSkill cs : careerSkills) {
            Long skillId = cs.getSkill().getSkillId();
            nameMap.put(skillId, cs.getSkill().getSkillName());
            monthMap.merge(skillId, months(cs), Integer::sum);
        }

        return monthMap.entrySet().stream()
                .map(e -> new SkillExperienceResponse(
                        e.getKey(),
                        nameMap.get(e.getKey()),
                        e.getValue() / 12,
                        e.getValue() % 12
                ))
                .sorted(Comparator.comparing(SkillExperienceResponse::getSkillName))
                .toList();
    }

    private List<CareerSkill> getCareerSkills(List<Career> careers) {
        return careers.stream()
                .flatMap(career -> careerSkillRepository.findByCareerCareerId(career.getCareerId()).stream())
                .toList();
    }

    private Map<Long, Integer> skillMonthsByProfile(String hrid) {
        List<Career> careers = careerRepository.findByProfileHrid(hrid);
        Map<Long, Integer> result = new HashMap<>();
        for (CareerSkill cs : getCareerSkills(careers)) {
            result.merge(cs.getSkill().getSkillId(), months(cs), Integer::sum);
        }
        return result;
    }

    private String commonSkillNames(Set<Long> commonSkillIds) {
        if (commonSkillIds.isEmpty()) return "";
        List<CareerSkill> allCareerSkills = careerSkillRepository.findAll();
        Map<Long, String> skillNameMap = new HashMap<>();
        for (CareerSkill cs : allCareerSkills) {
            skillNameMap.put(cs.getSkill().getSkillId(), cs.getSkill().getSkillName());
        }
        return commonSkillIds.stream()
                .map(skillNameMap::get)
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.joining(", "));
    }

    private double cosineSimilarity(Map<Long, Integer> a, Map<Long, Integer> b) {
        if (a.isEmpty() || b.isEmpty()) return 0.0;

        double dot = 0.0;
        for (Map.Entry<Long, Integer> e : a.entrySet()) {
            dot += e.getValue() * b.getOrDefault(e.getKey(), 0);
        }

        double normA = Math.sqrt(a.values().stream().mapToDouble(v -> v * v).sum());
        double normB = Math.sqrt(b.values().stream().mapToDouble(v -> v * v).sum());
        if (normA == 0.0 || normB == 0.0) return 0.0;

        return dot / (normA * normB);
    }

    private int months(CareerSkill cs) {
        return cs.getSkillYear() * 12 + cs.getSkillMonth();
    }
}
