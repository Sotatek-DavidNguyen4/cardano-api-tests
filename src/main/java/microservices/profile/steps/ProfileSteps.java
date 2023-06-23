package microservices.profile.steps;

import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.common.steps.BaseSteps;
import microservices.txn.steps.TransactionSteps;

import java.util.Map;

public class ProfileSteps extends BaseSteps {
    @Step("Get user info")
    public ProfileSteps when_getUserInfo(Map<String, Object> params) {
        sendGet(Endpoints.AccountsApi.GET_USER_INFO, params);
        return this;
    }
    @Step("Get user info")
    public ProfileSteps when_getBookmarkFillAllKey(Map<String, Object> params) {
        sendGet(Endpoints.AccountsApi.GET_BOOKMARK_FIND_ALL_KEY, params);
        return this;
    }
    @Step("Get user info")
    public ProfileSteps when_getNoteFillAll(Map<String, Object> params) {
        sendGet(Endpoints.AccountsApi.GET_NOTE_FIND_ALL, params);
        return this;
    }
}
