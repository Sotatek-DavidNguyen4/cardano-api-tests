package microservices.stakeKey.models.history;

import lombok.Data;

import java.util.ArrayList;

@Data
public class WithdrawalHistoryModel {
    private ArrayList<Object> data;
    private int totalItems;
    private int totalPages;
    private int currentPage;
}
