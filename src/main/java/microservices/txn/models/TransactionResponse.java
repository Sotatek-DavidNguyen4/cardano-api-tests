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

    @Data
    public class Summary {
        public ArrayList<StakeAddressTxInput> stakeAddressTxInputs;
        public ArrayList<StakeAddressTxOutput> stakeAddressTxOutputs;

        @Data
        public class StakeAddressTxInput{
            public String address;
            public int value;
            public ArrayList<Token> tokens;
            @Data
            public class Token{
                public String assetName;
                public int assetQuantity;
                public String assetId;
            }
        }
        @Data
        public class StakeAddressTxOutput{
            public String address;
            public int value;
            public ArrayList<StakeAddressTxInput.Token> tokens;
        }
    }

    @Data
    public class Contract{
        public String contract;
    }

    @Data
    public class Collaterals{
        public ArrayList<CollateralInputResponse> collateralInputResponses;
        public ArrayList<CollateralOutputResponse> collateralOutputResponses;
        @Data
        public class CollateralInputResponse{
            public String address;
            public String index;
            public String txHash;
            public int value;
            public ArrayList<Object> tokens;
        }
        @Data
        public class CollateralOutputResponse{
            public String address;
            public String index;
            public String txHash;
            public int value;
            public ArrayList<Object> tokens;
        }
    }
}
