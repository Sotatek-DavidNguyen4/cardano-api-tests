package microservices.stakeKey.steps;

import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.common.steps.BaseSteps;

import javax.xml.ws.Endpoint;
import java.util.Map;

public class StakeKeySteps extends BaseSteps {
    @Step("get a stake detail by address")
    public StakeKeySteps getStakeByAddress(Object address){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE_ADDRESS, "address", address);
        return this;
    }

}
