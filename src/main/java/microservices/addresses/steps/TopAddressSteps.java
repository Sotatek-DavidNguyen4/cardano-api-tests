package microservices.addresses.steps;

import com.google.common.collect.Ordering;
import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.addresses.models.AddressModel;
import microservices.addresses.models.TopAddressModel;
import microservices.block.models.BlockDetailModel;
import microservices.common.constants.RequestParams;
import microservices.common.steps.BaseSteps;
import microservices.stakeKey.models.topDelegators.TopDelegators;
import microservices.stakeKey.steps.StakeKeySteps;
import microservices.txn.models.TransactionResponse;
import microservices.txn.steps.TransactionSteps;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TopAddressSteps extends BaseSteps {
    @Step("get data for top address")
    public TopAddressSteps getDataForTopAddress(Map<String, Object> param){
        sendGet(Endpoints.AddressesApi.TOP_ADDRESS_URI, param);
        return this;
    }
    @Step("get data for top address no param")
    public TopAddressSteps getDataForTopAddressNoParam(){
        sendGet(Endpoints.AddressesApi.TOP_ADDRESS_URI);
        return this;
    }
    @Step("verify data response is on correct page")
    public TopAddressSteps verifyThatDataResponseIsOnCorrectPage(int expectedPage, int actualPage){
        Assert.assertEquals(expectedPage, actualPage);
        return this;
    }
    @Step("verify data amount is correct")
    public TopAddressSteps verifyDataAmountIsCorrect(int expectedSize, int ActualSize){
        Assert.assertEquals(expectedSize, ActualSize);
        return this;
    }
    @Step("verify data value is empty")
    public TopAddressSteps verifyDataValueIsEmpty(List attribute) {
        Assert.assertFalse(attribute.isEmpty(), "attribute is not empty");
        return this;
    }

    @Step("Verify currentPage and size of get top Address response")
    public TopAddressSteps then_verifyTopDelegatorsResponse(TopAddressModel topAddressModel, Map<String, Object> params) {
        RequestParams requestParams = new RequestParams(params, 0, 20);
        assertThat(topAddressModel.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        assertThat(topAddressModel.getData().size())
                .as("The size of page is wrong")
                .isEqualTo(requestParams.getSize());
        return this;
    }
    @Step("verify attribute is sorted correctly")
    public TopAddressSteps verifyAttributeIsSortedCorrectly(TopAddressModel topAddressModel) {
        List listBalance = topAddressModel.getData().stream().map(s -> s.getBalance()).collect(Collectors.toList());
        boolean sorted = Ordering.natural().reverse().isOrdered(listBalance);
        Assert.assertTrue(sorted);
        return this;
    }

    @Step("Verify response of address top and address detail")
    public TopAddressSteps then_verifyAddressResponseTopAndDetail(TopAddressModel topAddressModel, AddressModel addressModel) {

        assertThat(topAddressModel.getData().get(0).getTxCount())
                .as("Value of field 'txCount' is wrong")
                .isEqualTo(addressModel.getTxCount());
        assertThat(topAddressModel.getData().get(0).getBalance())
                .as("Value of field 'balance' is wrong")
                .isEqualTo(addressModel.getBalance());
        return this;
    }
}
