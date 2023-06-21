package tests.addresses;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.addresses.models.AddressModel;
import microservices.addresses.models.TopAddressModel;
import microservices.addresses.steps.AddressSteps;
import microservices.addresses.steps.TopAddressSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

@Epic("cardano")
@Feature("api-addresses")
public class AddressScenarioTests extends BaseTest {
    TopAddressSteps topAddressSteps = new TopAddressSteps();
    AddressSteps addressSteps = new AddressSteps();
    @Test(description = "Compare api Get: Get top address with api Get: Get a address detail", groups={"addresses", "address-scenario"})
    public void verifyGetAddressResponseSuccessfully(){
        TopAddressModel topAddressModel = (TopAddressModel) topAddressSteps
                .getDataForTopAddressNoParam()
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TopAddressModel.class);

        AddressModel addressModel = (AddressModel) addressSteps
                .getDataForAddress(topAddressModel.getData().get(0).getAddress())
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(AddressModel.class);

        topAddressSteps.then_verifyAddressResponseTopAndDetail(topAddressModel,addressModel);

    }
}
