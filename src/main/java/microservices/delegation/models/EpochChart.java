package microservices.delegation.models;

import java.util.List;
import lombok.Value;

@Value
public class EpochChart{
	List<DataByDaysItem> dataByDays;
	long highest;
	long lowest;
}