package microservices.token.models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TokenMintsModel {
    private String txHash;
    private String amount;
    @SerializedName("time")
    private String id;
}
