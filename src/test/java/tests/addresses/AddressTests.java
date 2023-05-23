package tests.addresses;

import base.BaseTest;
import microservices.addresses.constants.AddressConstants;
import microservices.addresses.models.AddressModel;
import microservices.addresses.steps.AddressSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

public class AddressTests extends BaseTest {
    AddressSteps addressSteps = new AddressSteps();
    Object address = "addr_test1vz09v9yfxguvlp0zsnrpa3tdtm7el8xufp3m5lsm7qxzclgmzkket";
    @Test(description = "verify that get data for address successfully", groups={"addresses", "address"})
    public void verifyGetAddressResponseSuccessfully(){

        AddressModel addressModel = (AddressModel) addressSteps
                .getDataForAddress(address)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(AddressModel.class);

        addressSteps.verifyValuesForAddress(addressModel, address);
    }
    @Test(description = "verify that get data for address unsuccessfully", groups={"addresses", "address"}, dataProvider = "paramInvalidAddress")
    public void verifyGetAddressResponseUnsuccessfully(Object address){
        addressSteps
                .getDataForAddress(address)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, AddressConstants.ERROR_MESSAGE, AddressConstants.ERROR_CODE);
    }
    @DataProvider(name ="paramInvalidAddress")
    public Object[][] dataSetInvalidAddress(){
        return new Object[][]{
                {123},
                //{"@#$%"},
                {" "},
                {"(token address):asset1c6t4elexwkpuzq08ssylhhmc78ahlz0sgw5a7y"},
                {"(NFT address): asset1c0vymmx0nysjaa8q5vah78jmuqyew3kjm48azr"}
        };
    }
}
