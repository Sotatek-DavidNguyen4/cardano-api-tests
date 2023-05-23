package microservices.addresses.models;

import lombok.Data;

import java.util.List;
@Data
public class DataItem{
	private String blockHash;
	private long totalOutput;
	private int epochSlotNo;
	private int fee;
	private int slot;
	private int epochNo;
	private long balance;
	private List<String> addressesInput;
	private int blockNo;
	private List<Object> tokens;
	private String time;
	private List<String> addressesOutput;
	private String hash;

}