package microservices.stakeKey.models;

import lombok.Data;

import java.util.ArrayList;
@Data
public class StakeModel {
    private String status;
    private String stakeAddress;
    private long  totalStake;
    private long rewardAvailable;
    private long rewardWithdrawn;
    private PoolModel pool;
    private ArrayList<Object> rewardPools;
}
