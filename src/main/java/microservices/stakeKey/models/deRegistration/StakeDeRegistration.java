package microservices.stakeKey.models.deRegistration;
import lombok.Data;

import java.util.List;

@Data
public class StakeDeRegistration {
    private List<StakeDeRegistrationData> data;
    private int totalItems;
    private int totalPages;
    private int currentPage;
}
