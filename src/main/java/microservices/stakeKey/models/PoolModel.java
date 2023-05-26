package microservices.stakeKey.models;

import lombok.Data;

@Data
public class PoolModel {
    private String tickerName;
    private String poolName;
    private String poolId;
}