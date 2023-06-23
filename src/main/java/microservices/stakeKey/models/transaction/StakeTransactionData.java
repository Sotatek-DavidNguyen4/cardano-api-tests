package microservices.stakeKey.models.transaction;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class StakeTransactionData {
    private String hash;
    private long blockNo;
    private String blockHash;
    private long epochNo;
    private long epochSlotNo;
    private long slot;
    private String time;
    private List<String> addressesInput;
    private List<String> addressesOutput;
    private long fee;
    private BigInteger totalOutput;
    private BigInteger balance;
    private List<Tokens> tokens;


    @Data
    public class Tokens{
        private long addressId;
        private String address;
        private String policy;
        private String name;
        private String displayName;
        private String fingerprint;
        private long quantity;
    }

}
