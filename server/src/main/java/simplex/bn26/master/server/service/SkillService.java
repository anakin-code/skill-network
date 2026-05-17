package simplex.bn26.master.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simplex.bn26.master.server.SkillException;
import simplex.bn26.master.server.dto.response.SkillResponse;
import simplex.bn26.master.server.entity.Skill;
import simplex.bn26.master.server.repository.SkillRepository;

import java.util.List;

@Service
@Transactional
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<SkillResponse> getSkillAll() {
        List<Skill> skills = skillRepository.findAll();
        return convertResponseList(skills);
    }

    public List<SkillResponse> searchSkill(String keyword) {
        List<Skill> results;

        if (keyword == null || keyword.isBlank()) {
            throw new SkillException("検索キーワードを入力してください");
        } else {
            results = skillRepository.findBySkillNameContainingIgnoreCase(keyword);
        }

        if (results.isEmpty()) {
            throw new SkillException("該当するスキルが見つかりませんでした。");
        }

        return convertResponseList(results);
    }

    public Skill createSkill(Skill skill) {
        if (skillRepository.existsBySkillName(skill.getSkillName())) {
            throw new SkillException("同じスキル名が既に存在します");
        }

        return skillRepository.save(skill);
    }

    public SkillResponse getSkill(Long skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new SkillException("スキルが見つかりません。skillId=" + skillId)
                );

        return new SkillResponse(skill.getSkillId(), skill.getSkillName());
    }

    public Skill postSkill(Long skillId, Skill request) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new SkillException("スキルが見つかりません。skillId=" + skillId)
                );

        skill.setSkillName(request.getSkillName());

        return skillRepository.save(skill);
    }

    public void deleteSkill(Long skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new SkillException("スキルが見つかりません。skillId=" + skillId)
                );

        skillRepository.delete(skill);
    }

    private List<SkillResponse> convertResponseList(List<Skill> skills) {
        return skills.stream()
                .map(skill -> new SkillResponse(
                        skill.getSkillId(),
                        skill.getSkillName()
                ))
                .toList();
    }
}