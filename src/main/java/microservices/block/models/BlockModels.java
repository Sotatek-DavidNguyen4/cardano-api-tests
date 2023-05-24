package microservices.block.models;

import lombok.Data;

import java.util.List;
@Data
public class BlockModels {
	private int totalItems;
	private List<DataItem> data;
	private int totalPages;
	private int currentPage;

}