package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.entity.Profile;
import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, String> {
    List<Profile> findByNameContaining(String name);
    List<Profile> findByMailAddressContaining(String mailAddress);
}
