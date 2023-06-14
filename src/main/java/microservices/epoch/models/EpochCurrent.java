package microservices.epoch.models;

import lombok.Data;

@Data
public class EpochCurrent {
    private Integer no;
    private Integer slot;
    private Integer totalSlot;
    private Integer account;
    private String startTime;
    private String endTime;
}
