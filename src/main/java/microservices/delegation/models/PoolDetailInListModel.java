package microservices.delegation.models;

import lombok.Data;

@Data
public class PoolDetailInListModel {
	private Object reward;
	private Object saturation;
	private int feeAmount;
	private Object feePercent;
	private Object poolSize;
	private String poolId;
	private long pledge;
	private int id;
	private Object poolName;

}
