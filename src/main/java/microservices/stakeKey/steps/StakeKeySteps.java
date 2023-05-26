package microservices.stakeKey.steps;

import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.common.constants.RequestParams;
import microservices.common.steps.BaseSteps;
import microservices.stakeKey.models.deRegistration.StakeDeRegistration;
import microservices.stakeKey.models.StakeHistory;
import org.testng.Assert;

import javax.xml.ws.Endpoint;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StakeKeySteps extends BaseSteps {
    @Step("get a stake detail by address")
    public StakeKeySteps getStakeByAddress(Object address){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE_ADDRESS, "address", address);
        return this;
    }
    @Step("get a stake instantaneous-rewards with stakeKey")
    public StakeKeySteps getStakeInstantaneousRewards(Object stakeKey){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE_INSTANTANEOUS_REWARDS, Endpoints.StakeKeyApi.STAKE_KEY, stakeKey);
        return this;
    }
    @Step("get a stake instantaneous rewards with param")
    public StakeKeySteps getStakeInstantaneousRewardParam(Object stakeKey, Map<String, Object> param){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE_INSTANTANEOUS_REWARDS, param, Endpoints.StakeKeyApi.STAKE_KEY, stakeKey);
        return this;
    }
    @Step("get Data For Stake De Registration with param")
    public StakeKeySteps getDataForStakeDeRegistration(Map<String, Object> param){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE_DE_REGISTRATION, param);
        return this;
    }
    @Step("get active stake, live stake and total stake")
    public StakeKeySteps getStakeAnalytics(){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE_ANALYTICS);
        return this;
    }
    @Step("Verify currentPage and size of get Data For Stake De Registration response")
    public StakeKeySteps then_verifyStakeDeRegistrationResponse(StakeDeRegistration stakeDeRegistration, Map<String, Object> params) {
        RequestParams requestParams = new RequestParams(params, 0, 20);
        assertThat(stakeDeRegistration.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        assertThat(stakeDeRegistration.getData().size())
                .as("The size of page is wrong")
                .isEqualTo(requestParams.getSize());
        return this;
    }

    @Step("get a stake with stakeKey")
    public StakeKeySteps getStakeWithStakeKey(Object stakeKey){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE, Endpoints.StakeKeyApi.STAKE_KEY, stakeKey);
        return this;
    }
    @Step("get stake history")
    public StakeKeySteps getStakeHistory(Object stakeKey){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE_HISTORY, Endpoints.StakeKeyApi.STAKE_KEY, stakeKey);
        return this;
    }
    @Step("get stake history all key")
    public StakeKeySteps getStakeHistoryAllKey(Object stakeKey, Map<String, Object> param){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE_HISTORY, param,  Endpoints.StakeKeyApi.STAKE_KEY, stakeKey);
        return this;
    }
    @Step("verify that current page")
    public StakeKeySteps then_verifyFilterStakeHistoryResponse(StakeHistory stakeHistory, Map<String,Object> param){
        RequestParams requestParams = new RequestParams(param, 0, 20);
        assertThat(stakeHistory.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        return this;
    }
}
