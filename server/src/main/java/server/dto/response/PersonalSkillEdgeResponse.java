package server.dto.response;

public class PersonalSkillEdgeResponse {
    private Long source;
    private Long target;
    private int months;
    private double normalizedWeight;

    public PersonalSkillEdgeResponse(Long source, Long target, int months, double normalizedWeight) {
        this.source = source;
        this.target = target;
        this.months = months;
        this.normalizedWeight = normalizedWeight;
    }

    public Long getSource() { return source; }
    public Long getTarget() { return target; }
    public int getMonths() { return months; }
    public double getNormalizedWeight() { return normalizedWeight; }
}
