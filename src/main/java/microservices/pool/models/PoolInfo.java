package microservices.pool.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class PoolInfo {
    public int txId;
    public String txHash;
    public String txTime;
    public int epoch;
    public int block;
    public int slotNo;
    public String poolName;
    public long pledge;
    public long cost;
    public double margin;
    public int poolId;
    public String poolView;
    public List<String> stakeKey;
}
