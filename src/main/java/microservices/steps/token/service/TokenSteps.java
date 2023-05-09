package microservices.steps.token.service;

import constants.EndPoints;
import core.BaseApi;
import io.qameta.allure.Step;

public class TokenSteps extends BaseApi {
    @Step
    public TokenSteps getListTokens(){
        sendGet(EndPoints.GET_LIST_TOKEN);
        return this;
    }
}
