package microservices.addresses.steps;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.addresses.constants.AddressConstants;
import microservices.addresses.models.AddressTransactionModel;
import microservices.block.models.BlockListModel;
import microservices.block.steps.BlockSteps;
import microservices.common.steps.BaseSteps;
import microservices.delegation.models.PoolListModel;
import microservices.delegation.steps.DelegationPoolListSteps;
import org.testng.Assert;
import util.AttributeStandard;

import java.util.Map;
import java.util.stream.Collectors;

import static constants.DateFormats.DATE_FORMAT;
import com.google.gson.JsonElement;


public class AddressTransactionSteps extends BaseSteps {
    @Step("get the transaction of address")
    public AddressTransactionSteps getTheTransactionOfAddress(Object address){
        sendGet(Endpoints.AddressesApi.ADDRESS_TRANSACTION_URI, AddressConstants.ADDRESS, address);
        System.out.println(getResponse().getBody().print());
        return this;
    }
    @Step("get the transaction of address")
    public AddressTransactionSteps getTheTransactionOfAddress(Object address, Map<String, Object> param){
        sendGet(Endpoints.AddressesApi.ADDRESS_TRANSACTION_URI, param, AddressConstants.ADDRESS, address);
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
    @Step("verify format attributes")
    public AddressTransactionSteps verifyFormatAttributes(AddressTransactionModel actualAddressInput){
        Assert.assertTrue(AttributeStandard.areValidHashes(actualAddressInput.getData().stream().map(s -> s.getHash()).collect(Collectors.toList())));
        Assert.assertTrue(AttributeStandard.areValidHashes(actualAddressInput.getData().stream().map(s -> s.getBlockHash()).collect(Collectors.toList())));
        Assert.assertTrue(AttributeStandard.areValidDates(actualAddressInput.getData().stream().map(s -> s.getTime()).collect(Collectors.toList()), DATE_FORMAT[0]));
        return this;
    }
    @Step("verify data attribute is null")
    public AddressTransactionSteps verifyDataAttributeIsNull(PoolListModel poolListModel){
        Assert.assertTrue(poolListModel.getData().isEmpty());
        return this;
    }
    @Step("verify data response is on correct page")
    public AddressTransactionSteps verifyThatDataResponseIsOnCorrectPage(Object expectedPage, Object actualPage){
        Assert.assertEquals(expectedPage, actualPage);
        return this;
    }
    @Step("verify data amount is correct")
    public AddressTransactionSteps verifyDataAmountIsCorrect(Object expectedSize, int ActualSize){
        Assert.assertEquals(expectedSize, ActualSize);
        return this;
    }
    @Step("verify value of attribute is correctly")
    public AddressTransactionSteps verifyValueOfAttributeIsCorrectly(AddressTransactionModel addressTransactionModel, Object value) {
        if(addressTransactionModel.getCurrentPage() <= 0){
            Assert.assertEquals(String.valueOf(addressTransactionModel.getCurrentPage()), value);
        }else{
            Assert.assertEquals(String.valueOf(addressTransactionModel.getCurrentPage()), value);
        }
        return this;
    }
    @Step("verify attribute exist")
    public AddressTransactionSteps verifyAttributeExist(){
        System.out.println(getResponse().getBody().asString());
        JsonParser parser = new JsonParser();
        JsonElement jsonObject = parser.parse(getResponse().getBody().asString()).getAsJsonObject();
        //System.out.println(isKeyPresent(getResponse().getBody().asString(), "hash"));
        Assert.assertTrue(getResponse().path("data") != null);
        Assert.assertTrue(getResponse().path("totalItems") != null);
        Assert.assertTrue(getResponse().path("totalPages") != null);
        Assert.assertTrue(getResponse().path("currentPage") != null);
        return this;
    }
}
