package microservices.addresses.steps;

import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.addresses.constants.AddressConstants;
import microservices.addresses.models.AddressTransactionModel;
import microservices.common.steps.BaseSteps;
import org.testng.Assert;

public class AddressTransactionSteps extends BaseSteps {
    @Step("get the transaction of address")
    public AddressTransactionSteps getTheTransactionOfAddress(Object address){
        sendGet(Endpoints.AddressesApi.ADDRESS_TRANSACTION_URI, AddressConstants.ADDRESS, address);
        return this;
    }
    @Step("verify address input is same as input data")
    public AddressTransactionSteps verifyAddressInputIsSameAsInputData(AddressTransactionModel actualAddressInput, String expectedAddressInput){
        boolean flag = actualAddressInput.getData().stream().map(s -> s.getAddressesInput()).anyMatch(x -> x.listIterator().next().equals(expectedAddressInput));
        Assert.assertTrue(flag,"all address input is same as input data");
        return this;
    }
    @Step("verify address output is same as input data")
    public AddressTransactionSteps verifyAddressInputIsSameAsOutputData(AddressTransactionModel actualAddressInput, String expectedAddressOutput){
        boolean flag = actualAddressInput.getData().stream().map(s -> s.getAddressesInput()).anyMatch(x -> x.listIterator().next().equals(expectedAddressOutput));
        Assert.assertTrue(flag,"all address input is same as input data");
        return this;
    }
}
