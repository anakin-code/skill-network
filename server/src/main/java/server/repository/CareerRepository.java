package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.entity.Career;
import java.util.List;

public interface CareerRepository extends JpaRepository<Career, Long> {
    List<Career> findByProfileHrid(String hrid);
    void deleteByProfileHrid(String hrid);
}
