package microservices.txn.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FilterTransactionResponse {
    public List<TransactionInfo> data;
    public long totalItems;
    public long totalPages;
    public long currentPage;
}
