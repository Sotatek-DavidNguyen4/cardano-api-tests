package microservices.delegation.steps;

import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;

import java.util.Map;

public class DelegationTopSteps extends BaseApi {
    @Step("get data for delegation pool top")
    public DelegationTopSteps getDataForDelegationHeader(Map<String, Object> param){
        sendGet(Endpoints.DelegationApi.POOL_TOP, param);
        return this;
    }
}
