package microservices.stakeKey.models.listAddress;

import lombok.Data;

import java.util.ArrayList;
@Data
public class StakeListAddressModel {
    private ArrayList<StakeListAddressDataModel> data;
    private long totalItems;
    private long totalPages;
    private long currentPage;
}
