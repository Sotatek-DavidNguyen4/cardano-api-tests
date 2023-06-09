package microservices.delegation.models;

import java.util.List;
import lombok.Value;

@Value
public class PoolDetailEpochs {
	int totalItems;
	List<PoolDetailEpoch> data;
	int totalPages;
	int currentPage;
}