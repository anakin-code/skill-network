package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.entity.Division;
import java.util.List;

public interface DivisionRepository extends JpaRepository<Division, Long> {
    List<Division> findByDivisionNameContaining(String keyword);
    List<Division> findByCompanyCompanyId(Long companyId);
}
