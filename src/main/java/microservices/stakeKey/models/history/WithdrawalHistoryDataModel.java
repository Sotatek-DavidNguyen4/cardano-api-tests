package microservices.stakeKey.models.history;

import lombok.Data;

@Data
public class WithdrawalHistoryDataModel {
    private String time;
    private int epochSlotNo;
    private long blockNo;
    private int fee;
    private int amount;
    private int epochNo;
    private String txHash;
    private long txId;
}
