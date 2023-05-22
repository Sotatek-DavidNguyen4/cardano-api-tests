package microservices.addresses.models;

import lombok.Data;

@Data
public class AddressModel {
	private boolean isContract;
	private String address;
	private long balance;
	private int txCount;
	private Object stakeAddress;
}
