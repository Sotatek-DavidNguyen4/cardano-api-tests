package microservices.stakeKey.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class StakeHistory {
    private ArrayList<DataHistory> data;
    private long totalItems;
    private long totalPages;
    private long currentPage;
}
