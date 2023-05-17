package microservices.epoch.models;

import lombok.Data;

@Data
public class EpochData {
    private int no;
    private String status;
    private int blkCount;
    private int outSum;
    private int txCount;
    private String startTime;
    private String endTime;
    private int maxSlot;
    private boolean rewardsDistributed;
}
