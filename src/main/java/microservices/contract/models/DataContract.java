package microservices.contract.models;

import lombok.Data;

@Data
public class DataContract {
    private String address;
    private int txCount;
    private int balance;
}
