package microservices.stakeKey.models.topDelegators;
import lombok.Data;

import java.util.List;

@Data
public class TopDelegators {
    private List<TopDelegatorsData> data;
    private int totalItems;
    private int totalPages;
    private int currentPage;
}
