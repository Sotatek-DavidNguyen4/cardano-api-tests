package microservices.delegation.steps;

import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;
import microservices.delegation.models.PoolDetailDelegatorModel;
import org.testng.Assert;
import util.AttributeStandard;

import java.util.Map;
import java.util.stream.Collectors;

import static constants.DateFormats.DATE_FORMAT;

public class DelegationTopSteps extends BaseApi {
    @Step("get data for delegation pool top")
    public DelegationTopSteps getDataForDelegationHeader(Map<String, Object> param){
        sendGet(Endpoints.DelegationApi.POOL_TOP, param);
        return this;
    }
    @Step("verify attribute exists or not")
    public DelegationTopSteps verifyDataNull(PoolDetailDelegatorModel poolDetailDelegatorModel){
        Assert.assertTrue(poolDetailDelegatorModel.getTotalPages() == 0);
        Assert.assertTrue(poolDetailDelegatorModel.getCurrentPage() == 0);
        return this;
    }
    @Step("verify data response is on correct page")
    public DelegationTopSteps verifyThatDataResponseIsOnCorrectPage(Object expectedPage, int actualPage){
        Assert.assertEquals(expectedPage, actualPage);
        return this;
    }
    @Step("verify data amount is correct")
    public DelegationTopSteps verifyDataAmountIsCorrect(Object expectedSize, int ActualSize){
        Assert.assertEquals(expectedSize, ActualSize);
        return this;
    }
    @Step("verify format attributes")
    public DelegationTopSteps verifyFormatAttributes(PoolDetailDelegatorModel poolDetailDelegatorModel){
        Assert.assertTrue(AttributeStandard.areValidDates(poolDetailDelegatorModel.getData().stream().map(s -> s.getTime()).collect(Collectors.toList()), DATE_FORMAT[0]));
        return this;
    }
}
