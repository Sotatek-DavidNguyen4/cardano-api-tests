package microservices.block.models;

import lombok.Data;

import java.util.List;
@Data
public class BlockListModel {
	private int totalItems;
	private List<BlockDetailModel> data;
	private int totalPages;
	private int currentPage;
}