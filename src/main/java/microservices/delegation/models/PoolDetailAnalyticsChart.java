package microservices.delegation.models;

import lombok.Value;

@Value
public class PoolDetailAnalyticsChart {
	EpochChart epochChart;
	DelegatorChart delegatorChart;
}