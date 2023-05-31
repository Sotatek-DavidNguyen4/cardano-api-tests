package microservices.stakeKey.models.history;

import lombok.Data;

import java.util.ArrayList;
@Data
public class DelegationHistoryModel {
    private ArrayList<DelegationHistoryDataModel> data;
    private int totalItems;
    private int totalPages;
    private int currentPage;
}
