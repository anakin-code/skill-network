package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.entity.Project;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByProjectNameContaining(String keyword);
    List<Project> findByDivisionDivisionId(Long divisionId);
}
