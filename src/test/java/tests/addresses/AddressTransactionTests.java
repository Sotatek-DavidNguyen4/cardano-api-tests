package tests.addresses;

import base.BaseTest;
import constants.Endpoints;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.addresses.constants.AddressConstants;
import microservices.addresses.models.AddressTransactionModel;
import microservices.addresses.steps.AddressTransactionSteps;
import microservices.common.steps.BaseSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

@Epic("cardano")
@Feature("api-addresses")
public class AddressTransactionTests extends BaseTest {
    AddressTransactionSteps addressSteps = new AddressTransactionSteps();

    String address = "DdzFFzCqrhsqU7rLVY4rjYgmHidTDLLfNpLDQk85QwCBu8WQW8Snpmq4rEmeZ2ze96WzBrLEt8FjTWU4f7cG7bsymDrpfKpDC5Jknpda";
    @Test(description = "get the list transaction of address successfully", groups = {"addresses", "address-transaction"})
    public void getTheListTransactionOfAddressSuccessfully(){
        AddressTransactionModel addressTransactionModel = (AddressTransactionModel) addressSteps
                .getTheTransactionOfAddress(address)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(AddressTransactionModel.class);

        addressSteps
                .verifyAddressInputIsSameAsInputData(addressTransactionModel, address)
                .verifyAddressInputIsSameAsOutputData(addressTransactionModel, address)
                .verifyFormatAttributes(addressTransactionModel)
                .verifyAttributeExist();

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
                //{"@#$%"},
                {""},
                {" "},
                {"(token address):asset1c6t4elexwkpuzq08ssylhhmc78ahlz0sgw5a7y"},
                {"(NFT address): asset1c0vymmx0nysjaa8q5vah78jmuqyew3kjm48azr"},
        };
    }
    @Test(description = "get the list transaction of address successfully with page", groups = {"addresses", "address-transaction"})
    public void getTheListTransactionOfAddressSuccessfullyWithPage(){
        address = "addr1vy6p2t2lspjhf2nr2g7hfygkxdeulw3vvr8yhrkyv9qvzncmulqgh";
        int page = 2;
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .getParamsMap();

        AddressTransactionModel addressTransactionModel = (AddressTransactionModel) addressSteps
                .getTheTransactionOfAddress(address, param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(AddressTransactionModel.class);

        addressSteps
                .verifyThatDataResponseIsOnCorrectPage(page, addressTransactionModel.getCurrentPage())
                .verifyValueOfAttributeIsCorrectly(addressTransactionModel, String.valueOf(page));

        page = addressTransactionModel.getTotalPages() + 1;

        param = new CreateParameters()
                .withPage(page)
                .getParamsMap();

        addressTransactionModel = (AddressTransactionModel) addressSteps
                .getTheTransactionOfAddress(address, param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(AddressTransactionModel.class);

        addressSteps
                .verifyThatDataResponseIsOnCorrectPage(page, addressTransactionModel.getCurrentPage())
                .verifyValueOfAttributeIsCorrectly(addressTransactionModel, String.valueOf(page));
    }
    @Test(description = "get the list transaction of address successfully with invalid page", groups = {"addresses", "address-transaction"}, dataProvider = "paramInvalidPage")
    public void getTheListTransactionOfAddressSuccessfullyWithInvalidPage(Object page){
        address = "addr1vy6p2t2lspjhf2nr2g7hfygkxdeulw3vvr8yhrkyv9qvzncmulqgh";
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .getParamsMap();

        AddressTransactionModel addressTransactionModel = (AddressTransactionModel) addressSteps
                .getTheTransactionOfAddress(address, param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(AddressTransactionModel.class);

        addressSteps
                .verifyValueOfAttributeIsCorrectly(addressTransactionModel, "0");
    }
    @DataProvider(name ="paramInvalidPage")
    public Object[][] dataSetInvalidPage(){
        return new Object[][]{
                {"a"},
                {"-10"},
                {" "},
                {"(jnfj#$%)"},
        };
    }
    @Test(description = "get the list transaction of address successfully with size", groups = {"addresses", "address-transaction"})
    public void getTheListTransactionOfAddressSuccessfullyWithSize(){
        address = "addr1vy6p2t2lspjhf2nr2g7hfygkxdeulw3vvr8yhrkyv9qvzncmulqgh";
        int size = 1;
        Map<String, Object> param = new CreateParameters()
                .withPageSize(size)
                .getParamsMap();

        AddressTransactionModel addressTransactionModel = (AddressTransactionModel) addressSteps
                .getTheTransactionOfAddress(address, param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(AddressTransactionModel.class);

        addressSteps.verifyDataAmountIsCorrect(size, addressTransactionModel.getData().size());
    }
    @Test(description = "get the list transaction of address successfully with invalid size", groups = {"addresses", "address-transaction"}, dataProvider = "paramInvalidSize")
    public void getTheListTransactionOfAddressSuccessfullyWithInvalidSize(Object size){
        address = "addr1vy6p2t2lspjhf2nr2g7hfygkxdeulw3vvr8yhrkyv9qvzncmulqgh";
        Map<String, Object> param = new CreateParameters()
                .withPageSize(size)
                .getParamsMap();

        AddressTransactionModel addressTransactionModel = (AddressTransactionModel) addressSteps
                .getTheTransactionOfAddress(address, param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(AddressTransactionModel.class);

        addressSteps.verifyDataAmountIsCorrect(20, addressTransactionModel.getData().size());
    }
    @DataProvider(name ="paramInvalidSize")
    public Object[][] dataSetInvalidSize(){
        return new Object[][]{
                {"a"},
                {"-2"},
                {" "},
                {"(jnfj#$%)"},
        };
    }
}
