package microservices.stakeKey.models.listAddress;

import lombok.Data;

@Data
public class StakeListAddressDataModel {
    private String address;
    private int txCount;
    private int balance;
}