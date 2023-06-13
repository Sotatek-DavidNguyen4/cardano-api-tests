package microservices.delegation.models;

import lombok.Value;

@Value
public class DataByDaysItem{
	int epochNo;
	long totalStake;
	int numberDelegator;
}