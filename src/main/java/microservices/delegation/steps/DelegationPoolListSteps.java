package microservices.delegation.steps;

import constants.AttributeFormats;
import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;
import microservices.addresses.steps.TopAddressSteps;
import microservices.delegation.models.PoolListModel;
import org.testng.Assert;
import util.AttributeStandard;

import java.util.Map;
import java.util.stream.Collectors;

public class DelegationPoolListSteps extends BaseApi {
    @Step("get data for pool list")
    public DelegationPoolListSteps getDataForPoolList(Map<String, Object> param){
        sendGet(Endpoints.DelegationApi.POOL_LIST_URI, param);
        return this;
    }
    @Step("verify attribute values")
    public DelegationPoolListSteps verifyAttributeValues(PoolListModel poolListModel){
        AttributeStandard.areValidPoolId(poolListModel.getData().stream().map(s -> s.getPoolId()).collect(Collectors.toList()));
        return this;
    }
    @Step("verify data attribute is null")
    public DelegationPoolListSteps verifyDatAttributeIsNull(PoolListModel poolListModel){
        Assert.assertTrue(poolListModel.getData().isEmpty());
        return this;
    }
    @Step("verify data response is on correct page")
    public DelegationPoolListSteps verifyThatDataResponseIsOnCorrectPage(int expectedPage, int actualPage){
        Assert.assertEquals(expectedPage, actualPage);
        return this;
    }
    @Step("verify data amount is correct")
    public DelegationPoolListSteps verifyDataAmountIsCorrect(int expectedSize, int ActualSize){
        Assert.assertEquals(expectedSize, ActualSize);
        return this;
    }
}
