package microservices.txn.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.assertj.core.internal.bytebuddy.build.Plugin;

import java.util.ArrayList;
import java.util.Date;

@Data
@AllArgsConstructor
public class TransactionResponse {
    public TransactionInfo tx;
    public Summary summary;
    public ArrayList<Contract> contracts;
    public Collaterals collaterals;
    public Object notes;
    public Object withdrawals;
    public Object delegations;
    public Object mints;
    public Protocol protocols;
    public ArrayList<StakeCertificates> stakeCertificates;
    public ArrayList<Pool> poolCertificates;
    public Object previousProtocols;
    public UtxOs utxOs;

    @Data
    public class Summary{
        public ArrayList<StakeAddress> stakeAddress;
    }
    @Data
    public class Contract{
        public String contract;
    }

    @Data
    public class Collaterals{
        public ArrayList<CollateralResponse> collateralInputResponses;
        public ArrayList<CollateralResponse> collateralOutputResponses;
        @Data
        public class CollateralResponse{
            public String address;
            public String index;
            public String txHash;
            public int value;
            public ArrayList<Object> tokens;
        }
    }

    @Data
    public class UtxOs{
        public ArrayList<WalletInfo> inputs;
        public ArrayList<WalletInfo> outputs;
    }

    @Data
    public class WalletInfo{
        public String address;
        public String index;
        public String txHash;
        public Object value;
        public ArrayList<Token> tokens;
        public String stakeAddress;
        public String assetId;
    }

    public class StakeAddress{
        public String address;
        public int value;
        public ArrayList<Token> tokens;
    }

    @Data
    public class Token{
        public String assetName;
        public long assetQuantity;
        public String assetId;
    }

    @Data
    public class Pool{
        public String poolId;
        public String vrfKey;
        public String rewardAccount;
        public ArrayList<String> poolOwners;
        public String metadataHash;
        public String metadataUrl;
        public double margin;
        public int cost;
        public int pledge;
        public String type;
    }

    @Data
    public class StakeCertificates{
        public String stakeAddress;
        public String type;
    }

    @Data
    public class Protocol{
        public long maxBlockExSteps;
        public long protocolMajor;
        public long protocolMinor;
    }
}
