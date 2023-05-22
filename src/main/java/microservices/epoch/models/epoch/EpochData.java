package microservices.epoch.models.epoch;

import lombok.Data;

import java.math.BigInteger;

@Data
public class EpochData {
    private Integer no;
    private String status;
    private Integer blkCount;
    private BigInteger outSum;
    private Integer txCount;
    private String startTime;
    private String endTime;
    private Integer maxSlot;
    private boolean rewardsDistributed;
}
