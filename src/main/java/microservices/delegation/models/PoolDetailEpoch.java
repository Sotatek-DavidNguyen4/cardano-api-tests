package microservices.delegation.models;

import lombok.Value;

@Value
public class PoolDetailEpoch {
	long stakeAmount;
	int delegators;
	int fee;
	Object ros;
	int epoch;
	int block;
}