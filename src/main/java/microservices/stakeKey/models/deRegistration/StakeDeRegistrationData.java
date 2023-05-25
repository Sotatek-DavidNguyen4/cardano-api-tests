package microservices.stakeKey.models.deRegistration;

import lombok.Data;

@Data
public class StakeDeRegistrationData {
    private Integer txId;
    private String txHash;
    private String txTime;
    private Integer block;
    private Integer epoch;
    private Integer slotNo;
    private Integer epochSlotNo;
    private String stakeKey;
}
