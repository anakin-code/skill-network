package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.entity.Rank;
import java.util.Optional;

public interface RankRepository extends JpaRepository<Rank, Long> {
    Optional<Rank> findByRankName(String rankName);
}
