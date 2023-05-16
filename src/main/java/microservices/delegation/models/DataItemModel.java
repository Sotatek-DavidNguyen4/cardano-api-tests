package microservices.delegation.models;

import lombok.Data;

@Data
public class DataItemModel {
	private String view;
	private int stakeAddressId;
	private int fee;
	private String time;
	private Object totalStake;
}
