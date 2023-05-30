package microservices.token.models;

import lombok.Data;

@Data
public class TokenMintsModel {
    private String txHash;
    private String amount;
    private String time;
}
