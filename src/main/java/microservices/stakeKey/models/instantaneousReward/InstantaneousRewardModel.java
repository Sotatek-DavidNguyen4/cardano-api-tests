package microservices.stakeKey.models.instantaneousReward;

import lombok.Data;

import java.util.ArrayList;
@Data
public class InstantaneousRewardModel {
    private ArrayList<InstantaneousRewardDataModel> data;
    private long totalItems;
    private long totalPages;
    private long currentPage;
}
