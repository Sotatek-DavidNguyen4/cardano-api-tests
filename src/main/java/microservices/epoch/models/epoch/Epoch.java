package microservices.epoch.models.epoch;

import lombok.Data;
import microservices.token.models.DataToken;

import java.util.ArrayList;

@Data
public class Epoch {
    private ArrayList<EpochData> data;
    private int totalItems;
    private int totalPages;
    private int currentPage;
}
