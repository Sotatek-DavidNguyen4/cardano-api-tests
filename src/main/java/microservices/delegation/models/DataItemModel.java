package microservices.delegation.models;

public class DataItemModel {
	private String view;
	private int stakeAddressId;
	private int fee;
	private String time;
	private Object totalStake;

	public String getView(){
		return view;
	}

	public int getStakeAddressId(){
		return stakeAddressId;
	}

	public int getFee(){
		return fee;
	}

	public String getTime(){
		return time;
	}

	public Object getTotalStake(){
		return totalStake;
	}
}
