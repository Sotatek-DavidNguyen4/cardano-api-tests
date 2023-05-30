package microservices.token.models;

import lombok.Data;

@Data
public class TokenTopHolderModel {
    private String address;
    private int quantity;
}
