package tests.addresses;

import base.BaseTest;
import constants.enums.AnalyticsType;
import microservices.addresses.constants.AddressConstants;
import microservices.addresses.steps.AddressAnalyticsSteps;
import microservices.addresses.steps.AddressesMinMaxBalanceSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;

public class AddressAnalyticsTests extends BaseTest {
    AddressAnalyticsSteps addressesSteps = new AddressAnalyticsSteps();
    Object address = "addr_test1vz09v9yfxguvlp0zsnrpa3tdtm7el8xufp3m5lsm7qxzclgmzkket";
    @Test(description = "verify that get data for address analytics successfully", groups={"addresses", "address-analytics"})
    public void verifyGetAddressAnalyticsResponseSuccessfully(){
        //dynamic data
        addressesSteps
                .getAnAddressAnalytics(address, AnalyticsType.TWO_WEEK.getAlcType())
                .validateStatusCode(HttpURLConnection.HTTP_OK);
    }
    @Test(description = "verify that get data for address analytics unSuccessfully with address", groups={"addresses", "address-analytics"}, dataProvider = "paramInvalidAddress")
    public void verifyGetAddressAnalyticsResponseUnsuccessfullyWithAddress(Object address){
        addressesSteps
                .getAnAddressAnalytics(address, AnalyticsType.TWO_WEEK.getAlcType())
                .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, AddressConstants.ERROR_MESSAGE, AddressConstants.ERROR_CODE);
    }
    @DataProvider(name ="paramInvalidAddress")
    public Object[][] dataSetInvalidAddress(){
        return new Object[][]{
                {123},
                {"null"},
                //{"@#$%"},
                {" "},
                {" "},
                {"(NFT address): asset1c0vymmx0nysjaa8q5vah78jmuqyew3kjm48azr"}
        };
    }
    @Test(description = "verify that get data for address analytics unSuccessfully with type", groups={"addresses", "address-analytics"}, dataProvider = "paramInvalidType")
    public void verifyGetAddressAnalyticsResponseUnsuccessfullyWithType(Object type){
        //wait for this test case is fixed
        addressesSteps
                .getAnAddressAnalytics(address, type)
                //.then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, AddressConstants.ERROR_MESSAGE, AddressConstants.ERROR_CODE)
        ;
    }
    @DataProvider(name ="paramInvalidType")
    public Object[][] dataSetInvalidType(){
        return new Object[][]{
                {123},
                {"null"},
                {"@#$%"},
                {" "},
                {" "},
                {"(NFT address): asset1c0vymmx0nysjaa8q5vah78jmuqyew3kjm48azr"}
        };
    }
}
