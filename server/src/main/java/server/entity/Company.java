package server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "company")
public class Company {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @NotBlank(message = "会社名を入力してください")
    @Size(max = 255, message = "会社名は255文字以内で入力してください")
    @Column(name = "company_name", length = 255, unique = true, nullable = false)
    private String companyName;

    @OneToMany(mappedBy = "company")
    private List<Division> divisions = new ArrayList<>();

    public Company() {}
    public Company(String companyName) { this.companyName = companyName; }
    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public List<Division> getDivisions() { return divisions; }
}
