package microservices.policy.models.detail;

import lombok.Data;

@Data
public class PolicyDetail {
    private String policyId;
    private Integer totalToken;
    private String policyScript;
}
