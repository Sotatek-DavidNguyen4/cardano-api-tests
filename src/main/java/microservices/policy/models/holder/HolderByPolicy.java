package microservices.policy.models.holder;

import lombok.Data;
import microservices.policy.models.token.TokenByPolicyData;

import java.util.List;
@Data
public class HolderByPolicy {
    private List<HolderByPolicyData> data;
    private int totalItems;
    private int totalPages;
    private int currentPage;
}
