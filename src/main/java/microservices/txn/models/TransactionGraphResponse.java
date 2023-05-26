package microservices.txn.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionGraphResponse {
    public String date;
    public int simpleTransactions;
    public int smartContract;
    public int metadata;
}
