package microservices.policy.models.token;

import lombok.Data;

import java.util.List;

@Data
public class TokenByPolicy {
    private List<TokenByPolicyData> data;
    private int totalItems;
    private int totalPages;
    private int currentPage;
}
