package microservices.token.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TokenTxsModel {
    private String hash;
    private int blockNo;
    private String blockHash;
    private int epochNo;
    private int epochSlotNo;
    private int slot;
    private String time;
    private ArrayList<Object> addressesInput;
    private ArrayList<Object> addressesOutput;
    private Object fee;
    private Object totalOutput;
}
