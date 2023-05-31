package microservices.addresses.steps;

import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.addresses.constants.AddressConstants;
import microservices.addresses.models.AddressAnalyticListModel;
import microservices.addresses.models.AddressTransactionModel;
import microservices.common.steps.BaseSteps;
import org.testng.Assert;
import util.AttributeStandard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static constants.DateFormats.DATE_FORMAT;


public class AddressAnalyticsSteps extends BaseSteps {
    @Step("get an adddress analytics")
    public AddressAnalyticsSteps getAnAddressAnalytics(Object address, Object type){
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put(AddressConstants.ADDRESS, address);
        pathParams.put(AddressConstants.TYPE, type);
        sendGetWithPathParams(Endpoints.AddressesApi.ADDRESS_ANALYTICS_URI, pathParams);
        return this;
    }
    @Step("verify format attributes")
    public AddressAnalyticsSteps verifyFormatAttributes(List<AddressAnalyticListModel> addressAnalytic){
        Assert.assertTrue(AttributeStandard.areValidDates(addressAnalytic.stream().map(s -> s.getDate()).collect(Collectors.toList()), DATE_FORMAT[2]));
        return this;
    }
}
