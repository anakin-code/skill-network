package server.dto.response;

public class SkillResponse {
    private Long skillId;
    private String skillName;
    public SkillResponse(Long skillId, String skillName) { this.skillId = skillId; this.skillName = skillName; }
    public Long getSkillId() { return skillId; }
    public String getSkillName() { return skillName; }
}
