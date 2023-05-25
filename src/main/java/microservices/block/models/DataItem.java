package microservices.block.models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DataItem{
	private int totalFees;
	private long totalOutput;
	private int epochNo;
	private int epochSlotNo;
	private int blockNo;
	private int txCount;
	private String slotLeader;
	@SerializedName("time")
	private String id;
	private int slotNo;
	private String hash;

}
