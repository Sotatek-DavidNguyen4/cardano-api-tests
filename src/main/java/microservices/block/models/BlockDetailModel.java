package microservices.block.models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;
@Data
public class BlockDetailModel {
	private int totalFees;
	private String blockHash;
	private long totalOutput;
	private int epochNo;
	private List<String> addressesInput;
	private int epochSlotNo;
	private int blockNo;
	private int txCount;
	private String slotLeader;
	@SerializedName("time")
	private String id;
	private int slotNo;
	private String hash;
	private int fee;
	private int slot;
	private List<String> addressesOutput;
}