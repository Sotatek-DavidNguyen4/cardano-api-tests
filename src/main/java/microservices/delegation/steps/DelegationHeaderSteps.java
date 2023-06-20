package microservices.delegation.steps;

import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;
import microservices.delegation.models.HeaderModel;
import org.testng.Assert;


public class DelegationHeaderSteps extends BaseApi {
    @Step("get data for delegation headers")
    public DelegationHeaderSteps getDataForDelegationHeader(){
        sendGet(Endpoints.DelegationApi.POOL_HEADER_URI);
        System.out.println(getResponse().getBody().print());
        return this;
    }
    @Step("verify attribute not null")
    public DelegationHeaderSteps verifyAttributeNotNull(HeaderModel headerModel){
        Assert.assertNotNull(headerModel.getEpochNo());
        Assert.assertNotNull(headerModel.getCountDownEndTime());
        Assert.assertNotNull(headerModel.getEpochNo());
        Assert.assertNotNull(headerModel.getLiveStake());
        Assert.assertNotNull(headerModel.getDelegators());
        return this;
    }

}
