package microservices.common.steps;

import core.BaseApi;
import io.qameta.allure.Step;
import microservices.common.models.ErrorResponse;
import org.testng.Assert;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseSteps extends BaseApi {
    @Step("Verify error response")
    public BaseSteps then_verifyErrorResponse(int statusCode, String message, String errorCode){
        validateResponse(statusCode);
        ErrorResponse response = getJsonAsObject(ErrorResponse.class);
        Assert.assertEquals(response.getErrorMessage(), message, "Incorrect error message");
        Assert.assertEquals(response.getErrorCode(), errorCode,"Incorrect error code");
        return this;
    }
}
