package microservices.epoch.models.epochByEpochNo;

import lombok.Data;

import java.math.BigInteger;

@Data
public class EpochDataByEpochNo {
    private Integer blockNo;
    private Long slotNo;
    private Integer epochNo;
    private Integer epochSlotNo;
    private String hash;
    private String time;
    private Long txCount;
    private BigInteger totalFees;
    private BigInteger totalOutput;
    private String slotLeader;
}
