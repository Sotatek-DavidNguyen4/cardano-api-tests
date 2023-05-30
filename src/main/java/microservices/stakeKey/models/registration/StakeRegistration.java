package microservices.stakeKey.models.registration;
import lombok.Data;

import java.util.List;

@Data
public class StakeRegistration {
    private List<StakeRegistrationData> data;
    private int totalItems;
    private int totalPages;
    private int currentPage;
}
