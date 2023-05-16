package microservices.delegation.models;

import lombok.Data;

@Data
public class HeaderModel {
	private int epochNo;
	private int epochSlotNo;
	private int liveStake;
	private int delegators;
	private int countDownEndTime;

}
