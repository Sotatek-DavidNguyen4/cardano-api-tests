package microservices.block.models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;
@Data
public class DataItem{
	private int totalFees;
	private String blockHash;
	private long totalOutput;
	private int epochNo;
	private int epochSlotNo;
	private List<String> addressesInput;
	private int blockNo;
	private int fee;
	private int slot;
	private String time;
	private List<String> addressesOutput;
	private String hash;
	private int txCount;
	private String slotLeader;
	@SerializedName("time")
	private String id;
	private int slotNo;
}