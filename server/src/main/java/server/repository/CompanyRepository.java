package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.entity.Company;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByCompanyNameContaining(String keyword);
    Optional<Company> findByCompanyName(String companyName);
}
