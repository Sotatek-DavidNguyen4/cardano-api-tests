package microservices.delegation.steps;

import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;

import java.util.Map;


public class DelegationPoolDetailAnalyticSteps extends BaseApi {

    @Step("get data for pool detail delegators")
    public DelegationPoolDetailAnalyticSteps getDataForPoolDetailAnalytics(Map<String, Object> param){
        sendGet(Endpoints.DelegationApi.POOL_DETAIL_ANALYTICS_URI, param);
        return this;
    }
}
