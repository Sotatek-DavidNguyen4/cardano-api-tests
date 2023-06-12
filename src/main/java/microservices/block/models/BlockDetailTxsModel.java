package microservices.block.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class BlockDetailTxsModel {
    private String hash;
    private int blockNo;
    private String blockHash;
    private int epochNo;
    private int epochSlotNo;
    private int slot;
    private String time;
    private ArrayList<Object> addressesInput;
    private ArrayList<Object> addressesOutput;
    private int fee;
    private int totalOutput;
    private int balance;
    private ArrayList<Object> tokens;
}
