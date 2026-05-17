package simplex.bn26.master.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simplex.bn26.master.server.entity.Rank;

import java.util.Optional;

@Repository
public interface RankRepository extends JpaRepository<Rank, Integer> {

    Optional<Rank> findByRankName(String rankName);
}