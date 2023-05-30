package microservices.stakeKey.models.registration;

import lombok.Data;

@Data
public class StakeRegistrationData {
    private Integer txId;
    private String txHash;
    private String txTime;
    private Integer block;
    private Integer epoch;
    private Integer slotNo;
    private Integer epochSlotNo;
    private String stakeKey;
}
