package microservices.delegation.steps;

import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.common.steps.BaseSteps;
import microservices.delegation.models.PoolDetailEpoch;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

import static util.AttributeStandard.*;

public class DelegationDetailEpchoSteps extends BaseSteps {
    @Step("get data for pool detail epochs")
    public DelegationDetailEpchoSteps getDataForPoolDetailEpochs(Map<String, Object> param){
        sendGet(Endpoints.DelegationApi.POOL_DETAIL_EPOCHS, param);
        return this;
    }
    @Step("verify data on right page")
    public DelegationDetailEpchoSteps verifyDataOnRightPage(Object actual, int expected){
        Assert.assertTrue(dataResponseIsOnCorrectPage(actual, expected));
        return this;
    }
    @Step("verify enough amount of data ")
    public DelegationDetailEpchoSteps verifyEnoughAmountOfData(Object actual, int expected){
        Assert.assertTrue(dataAmountIsCorrect(actual, expected));
        return this;
    }
    @Step("verify data value is empty ")
    public DelegationDetailEpchoSteps verifyDataValueIsEmpty(List<PoolDetailEpoch> data){
        Assert.assertTrue(dataValueIsEmpty(data));
        return this;
    }
}
