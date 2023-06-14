package microservices.stakeKey.models.topDelegators;

import lombok.Data;

@Data
public class TopDelegatorsData {
    private String stakeKey;
    private long balance;
    private String poolId;
    private String tickerName;
    private String poolName;
}
