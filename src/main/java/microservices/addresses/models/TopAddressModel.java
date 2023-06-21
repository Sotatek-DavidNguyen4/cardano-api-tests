package microservices.addresses.models;

import lombok.Data;

import java.util.List;

@Data
public class TopAddressModel {
	private int totalItems;
	private List<TopAddressDataModel> data;
	private int totalPages;
	private int currentPage;
}