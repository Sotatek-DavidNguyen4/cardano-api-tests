package microservices.addresses.models;

import lombok.Data;

import java.util.List;

@Data
public class AddressTransactionModel{
	private int totalItems;
	private List<DataItem> data;
	private int totalPages;
	private int currentPage;
}