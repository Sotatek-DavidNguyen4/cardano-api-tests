package microservices.delegation.models;

import lombok.Data;

import java.util.List;

@Data
public class PoolDetailHeaderModel {
    private int reward;
    private int margin;
    private int cost;
    private int delegators;
    private String hashView;
    private int epochBlock;
    private Object poolSize;
    private int stakeLimit;
    private List<String> ownerAccounts;
    private int lifetimeBlock;
    private List<String> rewardAccounts;
    private int saturation;
    private int ros;
    private Object tickerName;
    private long pledge;
    private Object poolName;
    private String createDate;

}
