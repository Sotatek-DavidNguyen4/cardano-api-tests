package microservices.epoch.models;

import lombok.Data;

@Data
public class EpochCurrent {
    private int no;
    private int slot;
    private int totalSlot;
    private int account;
}
