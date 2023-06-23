package microservices.common.steps;

import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;
import microservices.common.models.AuthLoginInput;
import microservices.common.models.ErrorResponse;
import org.testng.Assert;

public class BaseSteps extends BaseApi {
    @Step("Verify error response")
    public BaseSteps then_verifyErrorResponse(int statusCode, String message, String errorCode){
        validateResponse(statusCode);
        ErrorResponse response = getJsonAsObject(ErrorResponse.class);
        Assert.assertEquals(response.getErrorMessage(), message, "Incorrect error message");
        Assert.assertEquals(response.getErrorCode(), errorCode,"Incorrect error code");
        return this;
    }

    @Step("Auth service by email for login")
    public BaseSteps when_authLogin(String emailAddress, String userPassword, String type){
        removeHeaders();
        AuthLoginInput authLoginInput = new AuthLoginInput(emailAddress, userPassword, type);
        sendPost(Endpoints.AccountsApi.AUTH_LOGIN, authLoginInput);
        setHeader("access-token", getJsonValue("accessToken"));
        return this;
    }
}
