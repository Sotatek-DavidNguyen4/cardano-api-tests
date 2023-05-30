package microservices.stakeKey.models.withdrawalHistory;

import lombok.Data;
import microservices.token.models.TokenModel;

import java.util.ArrayList;

@Data
public class WithdrawalHistoryModel {
    private ArrayList<Object> data;
    private int totalItems;
    private int totalPages;
    private int currentPage;
}
