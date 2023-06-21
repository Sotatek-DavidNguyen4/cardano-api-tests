package microservices.stakeKey.models.history;

import lombok.Data;

@Data
public class DelegationHistoryDataModel {
    private String time;
    private String poolId;
    private int blockNo;
    private int epochNo;
    private String txHash;
    private int epochSlotNo;
    private String poolData;
    private String tickerName;
}