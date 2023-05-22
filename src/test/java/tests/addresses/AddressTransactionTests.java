package tests.addresses;

import base.BaseTest;
import constants.Endpoints;
import microservices.addresses.constants.AddressConstants;
import microservices.addresses.models.AddressTransactionModel;
import microservices.addresses.steps.AddressTransactionSteps;
import microservices.common.steps.BaseSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;

public class AddressTransactionTests extends BaseTest {
    AddressTransactionSteps addressSteps = new AddressTransactionSteps();

    String address = "addr_test1vz09v9yfxguvlp0zsnrpa3tdtm7el8xufp3m5lsm7qxzclgmzkket";
    @Test(description = "get the list transaction of address successfully", groups = {"addresses", "address-transaction"})
    public void getTheListTransactionOfAddressSuccessfully(){
        AddressTransactionModel addressTransactionModel = (AddressTransactionModel) addressSteps
                .getTheTransactionOfAddress(address)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(AddressTransactionTests.class);

        addressSteps
                .verifyAddressInputIsSameAsInputData(addressTransactionModel, address)
                .verifyAddressInputIsSameAsOutputData(addressTransactionModel, address);

    }
    @Test(description = "get the list transaction of address unsuccessfully", groups = {"addresses", "address-transaction"}, dataProvider = "paramInvalidAddress")
    public void getTheListTransactionOfAddressUnsuccessfully(Object address){
        addressSteps
                .getTheTransactionOfAddress(address)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, AddressConstants.ERROR_MESSAGE, AddressConstants.ERROR_CODE);

    }
    @DataProvider(name ="paramInvalidAddress")
    public Object[][] dataSetInvalidData(){
        return new Object[][]{
                {123},
                {"null"},
                //{"@#$%"},
                {" "},
                {"  "},
                {"(token address):asset1c6t4elexwkpuzq08ssylhhmc78ahlz0sgw5a7y"},
                {"(NFT address): asset1c0vymmx0nysjaa8q5vah78jmuqyew3kjm48azr"},
        };
    }
}
