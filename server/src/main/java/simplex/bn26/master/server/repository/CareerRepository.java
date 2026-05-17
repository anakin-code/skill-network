package simplex.bn26.master.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import simplex.bn26.master.server.entity.Career;

import java.util.List;

public interface CareerRepository extends JpaRepository<Career, Long> {

    List<Career> findByProfileHrid(String hrid);
}