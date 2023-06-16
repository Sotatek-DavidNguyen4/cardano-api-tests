package microservices.addresses.models;

import lombok.Data;

@Data
public class TopAddressDataModel {
	private String address;
	private long txCount;
	private long balance;

}
