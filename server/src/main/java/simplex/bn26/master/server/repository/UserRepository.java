package simplex.bn26.master.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simplex.bn26.master.server.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}