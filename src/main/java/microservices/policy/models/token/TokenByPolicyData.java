package microservices.policy.models.token;

import lombok.Data;
@Data
public class TokenByPolicyData {
    private String name;
    private String displayName;
    private String policy;
    private String fingerprint;
    private Integer txCount;
    private String supply;
    private String totalVolume;
    private String createdOn;
}
