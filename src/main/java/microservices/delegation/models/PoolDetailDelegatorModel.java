package microservices.delegation.models;

import java.util.List;

public class PoolDetailDelegatorModel {
	private int totalItems;
	private List<DataItemModel> data;
	private int totalPages;
	private int currentPage;

	public int getTotalItems(){
		return totalItems;
	}

	public List<DataItemModel> getData(){
		return data;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public int getCurrentPage(){
		return currentPage;
	}
}