package skill.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private int skillId;

    @NotBlank(message = "スキル名を入力してください")
    @Size(max = 20, message = "スキル名は20文字以内で入力してください")
    @Column(name = "skill_name", length = 20, unique = true)
    private String skillName;

    @OneToMany(
            mappedBy = "skill",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CareerSkill> careerSkills = new ArrayList<>();

    public Skill() {
    }

    public Skill(String skillName, int skillId) {
        this.skillName = skillName;
        this.skillId = skillId;
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

    public List<CareerSkill> getCareerSkills() {
        return careerSkills;
    }

    public void setCareerSkills(List<CareerSkill> careerSkills) {
        this.careerSkills = careerSkills;
    }
}