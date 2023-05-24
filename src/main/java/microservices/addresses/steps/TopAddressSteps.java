package microservices.addresses.steps;

import com.google.common.collect.Ordering;
import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.addresses.models.TopAddressModel;
import microservices.common.steps.BaseSteps;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TopAddressSteps extends BaseSteps {
    @Step("get data for top address")
    public TopAddressSteps getDataForTopAddress(Map<String, Object> param){
        sendGet(Endpoints.AddressesApi.TOP_ADDRESS_URI, param);
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
        Assert.assertTrue(attribute.isEmpty(),"attribute is empty");
        return this;
    }
    @Step("verify attribute is sorted correctly")
    public TopAddressSteps verifyAttributeIsSortedCorrectly(TopAddressModel topAddressModel) {
        List listBalance = topAddressModel.getData().stream().map(s -> s.getBalance()).collect(Collectors.toList());
        boolean sorted = Ordering.natural().reverse().isOrdered(listBalance);
        Assert.assertTrue(sorted);
        return this;
    }
}
