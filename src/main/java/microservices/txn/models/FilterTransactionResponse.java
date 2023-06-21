package microservices.txn.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class FilterTransactionResponse {
    public List<FilterTransactionDetail> data;
    public long totalItems;
    public long totalPages;
    public long currentPage;

    @Data
    public class FilterTransactionDetail {
        public String hash;
        public int blockNo;
        public String blockHash;
        public int epochNo;
        public int epochSlotNo;
        public int slot;
        public String time;
        public ArrayList<String> addressesInput;
        public ArrayList<String> addressesOutput;
        public long fee;
        @SerializedName("totalOutput")
        public long outSum;
        public Object balance;
        public ArrayList<Object> tokens;
    }
}
