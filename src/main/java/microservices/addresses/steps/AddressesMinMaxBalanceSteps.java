package microservices.addresses.steps;

import constants.Endpoints;
import microservices.addresses.constants.AddressConstants;
import microservices.common.steps.BaseSteps;

import java.util.Map;

public class AddressesMinMaxBalanceSteps extends BaseSteps {
    public AddressesMinMaxBalanceSteps getDataForMinMaxBalance(Object address){
        sendGet(Endpoints.AddressesApi.MIN_MAX_BALANCE_URI, AddressConstants.ADDRESS ,address);
        return this;
    }
}
