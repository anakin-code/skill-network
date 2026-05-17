package skill.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skill.server.entity.CareerSkill;

import java.util.List;

public interface CareerSkillRepository extends JpaRepository<CareerSkill, Long> {

    List<CareerSkill> findByCareerCareerId(Long careerId);
}