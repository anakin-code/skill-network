package simplex.bn26.master.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simplex.bn26.master.server.entity.Profile;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {

    List<Profile> findByMailAddressContaining(String mailAddress);

    List<Profile> findByNameContaining(String name);

    List<Profile> findByRankRankName(String rankName);

    List<Profile> findByHridContaining(String hrid);

    List<Profile> findByFree(String free);

    List<Profile> findByCareersProjectDivisionDivisionName(String divisionName);

    List<Profile> findByCareersProjectProjectNameContaining(String projectName);
}