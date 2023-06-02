package microservices.stakeKey.models.instantaneousReward;

import lombok.Data;

@Data
public class InstantaneousRewardDataModel {
    private String time;
    private int epochSlotNo;
    private long blockNo;
    private String amount;
    private int epochNo;
    private String txHash;
    private int blockIndex;
}