package simplex.bn26.master.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import simplex.bn26.master.server.entity.CareerSkill;

import java.util.List;

public interface CareerSkillRepository extends JpaRepository<CareerSkill, Long> {

    List<CareerSkill> findByCareerCareerId(Long careerId);
}