package skill.server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "career_skill")
public class CareerSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "career_skill_id")
    private Long careerSkillId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_id", nullable = false)
    private Career career;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    public CareerSkill() {
    }

    public CareerSkill(Long careerSkillId, Career career, Skill skill) {
        this.careerSkillId = careerSkillId;
        this.career = career;
        this.skill = skill;
    }

    public Long getCareerSkillId() {
        return careerSkillId;
    }

    public void setCareerSkillId(Long careerSkillId) {
        this.careerSkillId = careerSkillId;
    }

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}