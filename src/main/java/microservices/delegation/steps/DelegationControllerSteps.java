package microservices.delegation.steps;

import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;
import microservices.addresses.models.AddressTransactionModel;
import microservices.addresses.steps.AddressTransactionSteps;
import microservices.common.steps.BaseSteps;
import microservices.delegation.models.PoolDetailDelegatorModel;
import microservices.delegation.models.PoolDetailHeaderModel;
import org.testng.Assert;
import util.AttributeStandard;

import java.util.Map;
import java.util.stream.Collectors;

import static constants.DateFormats.DATE_FORMAT;
import static microservices.delegation.constants.DelegationConstant.PARAM_POOL_VIEW;

public class DelegationControllerSteps extends BaseSteps {

    @Step("get data for pool detail")
    public DelegationControllerSteps getDataForPoolDetail(Object poolView){
        sendGet(Endpoints.DelegationApi.POOL_DETAIL_URI, PARAM_POOL_VIEW, poolView);
        return this;
    }
    @Step("get data for pool detail delegators")
    public DelegationControllerSteps getDataForPoolDetailDelegator(Map<String, Object> param){
        sendGet(Endpoints.DelegationApi.POOL_DETAIL_DELEGATORS_URI, param);
        System.out.println(getResponse().getBody().print());
        return this;
    }
    @Step("verify attribute exists or not")
    public DelegationControllerSteps verifyAllAttributeExistsOrNot(PoolDetailHeaderModel actualPoolDetailHeader){
        Assert.assertNotNull(actualPoolDetailHeader.getHashView());
        Assert.assertNotNull(actualPoolDetailHeader.getCreateDate());
        return this;
    }
    @Step("verify attribute exists or not")
    public DelegationControllerSteps verifyDataNull(PoolDetailDelegatorModel poolDetailDelegatorModel){
        Assert.assertTrue(poolDetailDelegatorModel.getTotalPages() == 0);
        Assert.assertTrue(poolDetailDelegatorModel.getCurrentPage() == 0);
        return this;
    }
    @Step("verify data response is on correct page")
    public DelegationControllerSteps verifyThatDataResponseIsOnCorrectPage(Object expectedPage, int actualPage){
        Assert.assertEquals(expectedPage, actualPage);
        return this;
    }
    @Step("verify data amount is correct")
    public DelegationControllerSteps verifyDataAmountIsCorrect(Object expectedSize, int ActualSize){
        Assert.assertEquals(expectedSize, ActualSize);
        return this;
    }
    @Step("verify format attributes")
    public DelegationControllerSteps verifyFormatAttributes(PoolDetailDelegatorModel poolDetailDelegatorModel){
        Assert.assertTrue(AttributeStandard.areValidDates(poolDetailDelegatorModel.getData().stream().map(s -> s.getTime()).collect(Collectors.toList()), DATE_FORMAT[0]));
        return this;
    }
}
