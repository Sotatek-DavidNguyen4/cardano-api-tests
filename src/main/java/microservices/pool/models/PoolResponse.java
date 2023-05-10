package microservices.pool.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class PoolResponse {
    public List<PoolInfo> data;
    public int totalItems;
    public int totalPages;
    public int currentPage;
}
