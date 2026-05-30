package server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.dto.request.CreateSkillRequest;
import server.dto.response.*;
import simplex.bn26.master.server.dto.response.*;
import server.entity.CareerSkill;
import server.entity.Profile;
import server.entity.Skill;
import server.repository.CareerSkillRepository;
import server.repository.SkillRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SkillService {
    private final SkillRepository skillRepository;
    private final CareerSkillRepository careerSkillRepository;

    public SkillService(SkillRepository skillRepository, CareerSkillRepository careerSkillRepository) {
        this.skillRepository = skillRepository;
        this.careerSkillRepository = careerSkillRepository;
    }

    public List<SkillResponse> getSkillAll() {
        return skillRepository.findAll().stream().map(this::toResponse).toList();
    }

    public List<SkillResponse> searchSkills(String keyword) {
        if (keyword == null || keyword.isBlank()) return getSkillAll();
        return skillRepository.findBySkillNameContaining(keyword).stream().map(this::toResponse).toList();
    }

    public SkillResponse createSkill(CreateSkillRequest request) {
        Skill skill = new Skill(request.getSkillName());
        return toResponse(skillRepository.save(skill));
    }

    public void deleteSkill(Long skillId) {
        skillRepository.deleteById(skillId);
    }

    @Transactional(readOnly = true)
    public SkillNetworkResponse getSkillNetwork() {
        List<CareerSkill> careerSkills = careerSkillRepository.findAll();
        Map<Long, SkillNodeResponse> nodeMap = new HashMap<>();
        Map<Long, Integer> totalMonthsMap = new HashMap<>();
        Map<String, Integer> edgeMonthsMap = new HashMap<>();

        Map<Long, List<CareerSkill>> careerSkillMap = careerSkills.stream()
                .collect(Collectors.groupingBy(cs -> cs.getCareer().getCareerId()));

        for (CareerSkill cs : careerSkills) {
            Skill skill = cs.getSkill();
            int months = months(cs);
            totalMonthsMap.merge(skill.getSkillId(), months, Integer::sum);
            nodeMap.putIfAbsent(skill.getSkillId(), new SkillNodeResponse(skill.getSkillId(), skill.getSkillName(), 0));
        }

        for (List<CareerSkill> list : careerSkillMap.values()) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    CareerSkill a = list.get(i);
                    CareerSkill b = list.get(j);
                    Long skillIdA = a.getSkill().getSkillId();
                    Long skillIdB = b.getSkill().getSkillId();
                    if (skillIdA.equals(skillIdB)) continue;

                    Long source = Math.min(skillIdA, skillIdB);
                    Long target = Math.max(skillIdA, skillIdB);
                    int coMonths = Math.min(months(a), months(b));
                    edgeMonthsMap.merge(source + "-" + target, coMonths, Integer::sum);
                }
            }
        }

        List<SkillNodeResponse> nodes = nodeMap.values().stream()
                .map(n -> new SkillNodeResponse(n.getId(), n.getName(), totalMonthsMap.getOrDefault(n.getId(), 0)))
                .toList();

        List<SkillEdgeResponse> edges = edgeMonthsMap.entrySet().stream().map(entry -> {
            String[] ids = entry.getKey().split("-");
            Long source = Long.valueOf(ids[0]);
            Long target = Long.valueOf(ids[1]);
            int coMonths = entry.getValue();
            int denominator = Math.min(totalMonthsMap.getOrDefault(source, 0), totalMonthsMap.getOrDefault(target, 0));
            double normalizedWeight = denominator == 0 ? 0.0 : (double) coMonths / denominator;
            return new SkillEdgeResponse(source, target, coMonths, normalizedWeight);
        }).toList();

        return new SkillNetworkResponse(nodes, edges);
    }

    @Transactional(readOnly = true)
    public List<ExpertResponse> getExperts(String skillName) {
        Skill skill = skillRepository.findBySkillName(skillName)
                .orElseThrow(() -> new IllegalArgumentException("スキルが見つかりません: " + skillName));

        Map<String, ExpertAccumulator> map = new HashMap<>();

        for (CareerSkill cs : careerSkillRepository.findAll()) {
            if (!cs.getSkill().getSkillId().equals(skill.getSkillId())) continue;

            Profile profile = cs.getCareer().getProfile();
            ExpertAccumulator acc = map.computeIfAbsent(profile.getHrid(), key ->
                    new ExpertAccumulator(
                            profile.getHrid(),
                            profile.getName(),
                            profile.getMailAddress(),
                            profile.getRank() == null ? null : profile.getRank().getRankName()
                    )
            );
            acc.totalMonths += months(cs);
        }

        return map.values().stream()
                .sorted(Comparator.comparingInt((ExpertAccumulator e) -> e.totalMonths).reversed())
                .map(e -> new ExpertResponse(e.hrid, e.name, e.mailAddress, e.rankName, e.totalMonths))
                .toList();
    }

    private SkillResponse toResponse(Skill skill) {
        return new SkillResponse(skill.getSkillId(), skill.getSkillName());
    }

    private int months(CareerSkill cs) {
        return cs.getSkillYear() * 12 + cs.getSkillMonth();
    }

    private static class ExpertAccumulator {
        private final String hrid;
        private final String name;
        private final String mailAddress;
        private final String rankName;
        private int totalMonths;

        private ExpertAccumulator(String hrid, String name, String mailAddress, String rankName) {
            this.hrid = hrid;
            this.name = name;
            this.mailAddress = mailAddress;
            this.rankName = rankName;
        }
    }
}
