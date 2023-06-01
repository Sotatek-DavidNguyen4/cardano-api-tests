package microservices.delegation.models;

import lombok.Data;

import java.util.List;
@Data
public class PoolListModel {
	private int totalItems;
	private List<PoolDetailInListModel> data;
	private int totalPages;
	private int currentPage;

}