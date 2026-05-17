package simplex.bn26.master.server.dto.response;

public class SkillResponse {

    private int skillId;

    private String skillName;

    public SkillResponse() {
    }

    public SkillResponse(int skillId, String skillName) {
        this.skillId = skillId;
        this.skillName = skillName;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
}