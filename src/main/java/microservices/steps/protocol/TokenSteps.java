package microservices.steps.protocol;

import constants.EndPoints;
import core.BaseApi;
import io.qameta.allure.Step;

public class TokenSteps extends BaseApi {
    @Step
    public String getTokens(){
        sendGet(EndPoints.GET_TOKENS);
        return getJsonAsString();
    }
}
