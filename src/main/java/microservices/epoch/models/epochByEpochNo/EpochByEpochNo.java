package microservices.epoch.models.epochByEpochNo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EpochByEpochNo {
    private List<EpochDataByEpochNo> data;
    private Integer totalItems;
    private Integer totalPages;
    private Integer currentPage;
}
