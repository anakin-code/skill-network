package server.dto.response;

public class PersonalSkillNodeResponse {
    private Long id;
    private String label;
    private int months;
    private double normalizedSize;

    public PersonalSkillNodeResponse(Long id, String label, int months, double normalizedSize) {
        this.id = id;
        this.label = label;
        this.months = months;
        this.normalizedSize = normalizedSize;
    }

    public Long getId() { return id; }
    public String getLabel() { return label; }
    public int getMonths() { return months; }
    public double getNormalizedSize() { return normalizedSize; }
}
