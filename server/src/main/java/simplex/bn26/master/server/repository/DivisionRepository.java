package simplex.bn26.master.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simplex.bn26.master.server.entity.Division;

import java.util.Optional;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Integer> {

    Optional<Division> findByDivisionName(String divisionName);
}
