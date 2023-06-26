package microservices.profile.steps;

import constants.Endpoints;
import data.DataUser;
import io.qameta.allure.Step;
import microservices.common.steps.BaseSteps;
import microservices.profile.models.UserInfoResponse;
import microservices.txn.steps.TransactionSteps;

import java.util.Map;

import static org.testng.Assert.assertTrue;

public class ProfileSteps extends BaseSteps {
    @Step("Get user info")
    public ProfileSteps when_getUserInfo(Map<String, Object> params) {
        sendGet(Endpoints.AccountsApi.GET_USER_INFO, params);
        return this;
    }
    @Step("Get bookmark fill all key")
    public ProfileSteps when_getBookmarkFillAllKey(Map<String, Object> params) {
        sendGet(Endpoints.AccountsApi.GET_BOOKMARK_FIND_ALL_KEY, params);
        return this;
    }
    @Step("Get note fill all")
    public ProfileSteps when_getNoteFillAll(Map<String, Object> params) {
        sendGet(Endpoints.AccountsApi.GET_NOTE_FIND_ALL, params);
        return this;
    }
    @Step("Verify user info response")
    public ProfileSteps then_verifyUserInfoResponse(UserInfoResponse userInfoResponse, DataUser user) {
        assertTrue(userInfoResponse.getEmail().equals(user.getEmail()));
        return this;
    }
}
