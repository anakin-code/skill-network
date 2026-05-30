package server.dto.response;

public class SkillNodeResponse {
    private Long id;
    private String name;
    private int totalMonths;
    public SkillNodeResponse(Long id, String name, int totalMonths) { this.id = id; this.name = name; this.totalMonths = totalMonths; }
    public Long getId() { return id; }
    public String getName() { return name; }
    public int getTotalMonths() { return totalMonths; }
}
