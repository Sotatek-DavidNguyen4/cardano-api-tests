package microservices.epoch.models;

import lombok.Data;

@Data
public class EpochCurrent {
    private Object no;
    private Object slot;
    private int totalSlot;
    private Object account;
}
