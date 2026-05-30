package server.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import server.entity.CareerSkill;
import java.util.List;

public interface CareerSkillRepository extends JpaRepository<CareerSkill, Long> {
    @EntityGraph(attributePaths = {"career", "skill"})
    List<CareerSkill> findAll();
    List<CareerSkill> findByCareerCareerId(Long careerId);
}
