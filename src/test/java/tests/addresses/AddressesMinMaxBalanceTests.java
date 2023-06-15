package tests.addresses;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.addresses.constants.AddressConstants;
import microservices.addresses.models.AddressModel;
import microservices.addresses.steps.AddressSteps;
import microservices.addresses.steps.AddressesMinMaxBalanceSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;
@Epic("cardano")
@Feature("api-addresses")
public class AddressesMinMaxBalanceTests extends BaseTest {
    AddressesMinMaxBalanceSteps addressesMinMaxBalanceSteps = new AddressesMinMaxBalanceSteps();
    Object address = "addr1vy6p2t2lspjhf2nr2g7hfygkxdeulw3vvr8yhrkyv9qvzncmulqgh";
    @Test(description = "verify that get data for address mix-max-balance successfully", groups={"addresses", "min-max-balance"})
    public void verifyGetAddressMinMaxBalanceResponseSuccessfully(){
        addressesMinMaxBalanceSteps
                .getDataForMinMaxBalance(address)
                .validateStatusCode(HttpURLConnection.HTTP_OK);
    }
    @Test(description = "verify that get data for address mix-max-balance unsuccessfully", groups={"addresses", "min-max-balance"}, dataProvider = "paramInvalidAddress")
    public void verifyGetAddressMinMaxBalanceResponseUnsuccessfully(Object address){
        addressesMinMaxBalanceSteps
                .getDataForMinMaxBalance(address)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, AddressConstants.ERROR_MESSAGE, AddressConstants.ERROR_CODE);
    }
    @DataProvider(name ="paramInvalidAddress")
    public Object[][] dataSetInvalidAddress(){
        return new Object[][]{
                {123},
                //{"@#$%"},
                {" "},
                {"(NFT address): asset1c0vymmx0nysjaa8q5vah78jmuqyew3kjm48azr"}
        };
    }
}
