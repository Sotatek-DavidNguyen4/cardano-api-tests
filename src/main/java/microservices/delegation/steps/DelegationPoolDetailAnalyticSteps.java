package microservices.delegation.steps;

import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;
import microservices.common.steps.BaseSteps;
import microservices.delegation.models.PoolDetailAnalyticsChart;
import microservices.delegation.models.PoolDetailInListModel;

import java.util.Map;


public class DelegationPoolDetailAnalyticSteps extends BaseSteps {

    @Step("get data for pool detail delegators")
    public DelegationPoolDetailAnalyticSteps getDataForPoolDetailAnalytics(Map<String, Object> param){
        sendGet(Endpoints.DelegationApi.POOL_DETAIL_ANALYTICS_URI, param);
        return this;
    }
}
