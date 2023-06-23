package microservices.stakeKey.models.transaction;
import lombok.Data;

import java.util.List;

@Data
public class StakeTransaction {
    private List<StakeTransactionData> data;
    private int totalItems;
    private int totalPages;
    private int currentPage;
}
