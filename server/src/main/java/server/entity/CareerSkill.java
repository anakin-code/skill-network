package server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "career_skill")
public class CareerSkill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "career_skill_id")
    private Long careerSkillId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_id", nullable = false)
    private Career career;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(name = "skill_year", nullable = false)
    private int skillYear;

    @Column(name = "skill_month", nullable = false)
    private int skillMonth;

    public CareerSkill() {}
    public CareerSkill(Career career, Skill skill, int skillYear, int skillMonth) {
        this.career = career; this.skill = skill; this.skillYear = skillYear; this.skillMonth = skillMonth;
    }
    public Long getCareerSkillId() { return careerSkillId; }
    public void setCareerSkillId(Long careerSkillId) { this.careerSkillId = careerSkillId; }
    public Career getCareer() { return career; }
    public void setCareer(Career career) { this.career = career; }
    public Skill getSkill() { return skill; }
    public void setSkill(Skill skill) { this.skill = skill; }
    public int getSkillYear() { return skillYear; }
    public void setSkillYear(int skillYear) { this.skillYear = skillYear; }
    public int getSkillMonth() { return skillMonth; }
    public void setSkillMonth(int skillMonth) { this.skillMonth = skillMonth; }
}
