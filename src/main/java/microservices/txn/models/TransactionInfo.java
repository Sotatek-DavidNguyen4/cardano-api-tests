package microservices.txn.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionInfo {
    public String hash;
    public String time;
    public int blockNo;
    public String blockHash;
    public int epochSlot;
    public int maxEpochSlot;
    public int epochNo;
    public String status;
    public int confirmation;
    public long fee;
    public long totalOutput;
}
