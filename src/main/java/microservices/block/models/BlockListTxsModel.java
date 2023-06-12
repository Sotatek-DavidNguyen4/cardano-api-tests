package microservices.block.models;

import lombok.Data;

import java.util.List;

@Data
public class BlockListTxsModel {
    private int totalItems;
    private List<BlockDetailTxsModel> data;
    private int totalPages;
    private int currentPage;
}
