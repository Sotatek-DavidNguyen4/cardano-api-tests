package microservices.delegation.models;

import java.util.List;
import lombok.Value;

@Value
public class DelegatorChart{
	List<DataByDaysItem> dataByDays;
	int highest;
	int lowest;
}