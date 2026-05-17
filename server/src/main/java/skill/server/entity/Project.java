package skill.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @NotBlank(message = "プロジェクト名を入力してください")
    @Size(max = 255, message = "プロジェクト名は255文字以内で入力してください")
    @Column(name = "project_name", length = 255, unique = true)
    private String projectName;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "division_id")
    private Division division;

    @Column(name = "project_content", columnDefinition = "TEXT")
    private String projectContent;

    @OneToMany(mappedBy = "project")
    private List<Career> careers = new ArrayList<>();

    public Project() {
    }

    public Project(
            Long projectId,
            String projectName,
            Division division,
            String projectContent,
            List<Career> careers
    ) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.division = division;
        this.projectContent = projectContent;
        this.careers = careers;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public String getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }

    public List<Career> getCareers() {
        return careers;
    }

    public void setCareers(List<Career> careers) {
        this.careers = careers;
    }
}