package server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "skill")
public class Skill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long skillId;

    @NotBlank(message = "スキル名を入力してください")
    @Size(max = 255, message = "スキル名は255文字以内で入力してください")
    @Column(name = "skill_name", length = 255, unique = true, nullable = false)
    private String skillName;

    public Skill() {}
    public Skill(String skillName) { this.skillName = skillName; }
    public Long getSkillId() { return skillId; }
    public void setSkillId(Long skillId) { this.skillId = skillId; }
    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
}
