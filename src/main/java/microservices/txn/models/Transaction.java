package microservices.txn.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Transaction {
    public int blockNo;
    public ArrayList<String> fromAddress;
    public ArrayList<String> toAddress;
    public long amount;
    public String hash;
    public int epochNo;
    public int epochSlotNo;
    public int slot;
    public String time;
    public String status;
}
