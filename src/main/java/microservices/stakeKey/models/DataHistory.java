package microservices.stakeKey.models;

import lombok.Data;

@Data
public class DataHistory {
    private String time;
    private int blockIndex;
    private String action;
    private int fee;
    private int blockNo;
    private int epochSlotNo;
    private String txHash;
    private int deposit;
    private int epochNo;
}