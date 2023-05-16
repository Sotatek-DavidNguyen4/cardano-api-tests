package microservices.delegation.models;

import lombok.Data;

import java.util.List;
@Data
public class PoolDetailDelegatorModel {
	private int totalItems;
	private List<DataItemModel> data;
	private int totalPages;
	private int currentPage;

}