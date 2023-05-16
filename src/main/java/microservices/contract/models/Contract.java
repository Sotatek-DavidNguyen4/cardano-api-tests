package microservices.contract.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Contract {
    private ArrayList<DataContract> dataContracts;
    private int totalItems;
    private int totalPages;
    private int currentPage;
}
