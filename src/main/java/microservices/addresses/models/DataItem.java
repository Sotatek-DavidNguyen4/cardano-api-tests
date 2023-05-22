package microservices.addresses.models;

import java.util.List;

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

	public String getBlockHash(){
		return blockHash;
	}

	public long getTotalOutput(){
		return totalOutput;
	}

	public int getEpochSlotNo(){
		return epochSlotNo;
	}

	public int getFee(){
		return fee;
	}

	public int getSlot(){
		return slot;
	}

	public int getEpochNo(){
		return epochNo;
	}

	public long getBalance(){
		return balance;
	}

	public List<String> getAddressesInput(){
		return addressesInput;
	}

	public int getBlockNo(){
		return blockNo;
	}

	public List<Object> getTokens(){
		return tokens;
	}

	public String getTime(){
		return time;
	}

	public List<String> getAddressesOutput(){
		return addressesOutput;
	}

	public String getHash(){
		return hash;
	}
}