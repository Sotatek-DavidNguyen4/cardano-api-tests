package microservices.policy.models.holder;

import lombok.Data;

@Data
public class HolderByPolicyData {
    private String address;
    private String name;
    private String displayName;
    private String fingerprint;
    private Integer quantity;
}
