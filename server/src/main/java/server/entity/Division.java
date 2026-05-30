package server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "division")
public class Division {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "division_id")
    private Long divisionId;

    @NotBlank(message = "部署名を入力してください")
    @Size(max = 255, message = "部署名は255文字以内で入力してください")
    @Column(name = "division_name", length = 255, unique = true, nullable = false)
    private String divisionName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public Division() {}
    public Division(String divisionName, Company company) { this.divisionName = divisionName; this.company = company; }
    public Long getDivisionId() { return divisionId; }
    public void setDivisionId(Long divisionId) { this.divisionId = divisionId; }
    public String getDivisionName() { return divisionName; }
    public void setDivisionName(String divisionName) { this.divisionName = divisionName; }
    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }
}
