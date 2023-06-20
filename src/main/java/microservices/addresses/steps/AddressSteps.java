package microservices.addresses.steps;

import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.addresses.constants.AddressConstants;
import microservices.addresses.models.AddressModel;
import microservices.common.steps.BaseSteps;
import org.apache.http.impl.io.IdentityInputStream;
import org.testng.Assert;

import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AddressSteps extends BaseSteps {
    @Step("get data for address")
    public AddressSteps getDataForAddress(Object address){
        sendGet(Endpoints.AddressesApi.ADDRESS_URI, AddressConstants.ADDRESS, address);
        System.out.println(getResponse().getBody().print());
        return this;
    }
    @Step("verify values for address ")
    public AddressSteps verifyValuesForAddress(AddressModel addressModel, Object ...attributes){
        assertThat(addressModel.getAddress())
                .as("equal to expected address")
                .isEqualTo(attributes[0]);
        return this;
    }
}
