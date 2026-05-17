package skill.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skill.server.entity.Skill;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findBySkillNameContainingIgnoreCase(String keyword);

    boolean existsBySkillName(String skillName);

    @Query("""
            SELECT DISTINCT s FROM Skill s
            LEFT JOIN s.careerSkills cs
            LEFT JOIN cs.career c
            LEFT JOIN c.project p
            WHERE LOWER(s.skillName) LIKE LOWER(CONCAT('%', :contentKeyword, '%'))
               OR LOWER(p.projectContent) LIKE LOWER(CONCAT('%', :contentKeyword, '%'))
            """)
    List<Skill> findBySkillNameContainingProjectContent(String contentKeyword);
}