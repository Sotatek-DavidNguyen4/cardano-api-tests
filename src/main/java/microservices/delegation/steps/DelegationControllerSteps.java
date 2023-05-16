package microservices.delegation.steps;

import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;
import microservices.delegation.models.PoolDetailDelegatorModel;
import microservices.delegation.models.PoolDetailHeaderModel;
import org.testng.Assert;

import java.util.Map;

import static microservices.delegation.constants.DelegationConstant.PARAM_POOL_VIEW;

public class DelegationControllerSteps extends BaseApi {

    @Step("get data for pool detail")
    public DelegationControllerSteps getDataForPoolDetail(Object poolView){
        sendGet(Endpoints.DelegationApi.POOL_DETAIL_URI, PARAM_POOL_VIEW, poolView);
        return this;
    }
    @Step("get data for pool detail delegators")
    public DelegationControllerSteps getDataForPoolDetailDelegator(Map<String, Object> param){
        sendGet(Endpoints.DelegationApi.POOL_DETAIL_DELEGATORS_URI, param);
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
}
