package server.dto.response;

public class SkillEdgeResponse {
    private Long source;
    private Long target;
    private int coMonths;
    private double normalizedWeight;
    public SkillEdgeResponse(Long source, Long target, int coMonths, double normalizedWeight) { this.source = source; this.target = target; this.coMonths = coMonths; this.normalizedWeight = normalizedWeight; }
    public Long getSource() { return source; }
    public Long getTarget() { return target; }
    public int getCoMonths() { return coMonths; }
    public double getNormalizedWeight() { return normalizedWeight; }
}
