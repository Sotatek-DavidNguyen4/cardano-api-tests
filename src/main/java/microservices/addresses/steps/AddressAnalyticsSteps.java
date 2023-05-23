package microservices.addresses.steps;

import constants.Endpoints;
import microservices.addresses.constants.AddressConstants;
import microservices.common.steps.BaseSteps;

import java.util.HashMap;
import java.util.Map;


public class AddressAnalyticsSteps extends BaseSteps {
    public AddressAnalyticsSteps getAnAddressAnalytics(Object address, Object type){
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put(AddressConstants.ADDRESS, address);
        pathParams.put(AddressConstants.TYPE, type);
        sendGetWithPathParams(Endpoints.AddressesApi.ADDRESS_ANALYTICS_URI, pathParams);
        return this;
    }
}
