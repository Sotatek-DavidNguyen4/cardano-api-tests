package tests.profile;

import base.BaseTest;
import constants.Endpoints;
import constants.Url;
import core.BaseApi;
import data.ApiResponseData;
import data.ApiUser;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import microservices.common.constants.APIErrorCode;
import microservices.common.constants.APIErrorMessage;
import microservices.profile.constants.ProfileContants;
import microservices.profile.models.SignInResponse;
import microservices.profile.models.UserInfoResponse;
import microservices.profile.steps.ProfileSteps;
import microservices.txn.models.FilterTransactionResponse;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;

import static data.ApiResponseData.*;
import static data.ApiResponseData.TRANSACTION_ALLEGRA_ERA;
import static data.ApiUser.USER_SIGN_IN;

@Epic("cardano")
@Feature("api-authentication")
@Story("Profile test")
public class ProfileTest extends BaseTest {
    private ProfileSteps profileSteps = new ProfileSteps();
    @BeforeMethod(alwaysRun = true)
    public void preconditions() throws Exception {
        new Url("preprodProfile");
        new ApiResponseData();
        new ApiUser();
        BaseApi.initReqSpec();
        BaseApi.setBaseUrl(Url.API);
    }

    @Test(description = "Get user info success", groups = "profile")
    public void get_user_info_success() {
        // network = MAIN_NET
        baseSteps.when_authLogin(USER_SIGN_IN);
        String pathUserSchema = "schemaJson/profile/userInfo.json";
        MultiMap params = new MultiValueMap();
        params.put("network", ProfileContants.NETWORK[0]);
        UserInfoResponse userInfoResponse = (UserInfoResponse) profileSteps.when_getUserInfo(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(pathUserSchema)
                .saveResponseObject(FilterTransactionResponse.class);
        System.out.println(userInfoResponse);
        profileSteps.then_verifyUserInfoResponse(userInfoResponse, USER_SIGN_IN);

        // network = PRE_PROD
        params = new MultiValueMap();
        params.put("network", ProfileContants.NETWORK[1]);
        userInfoResponse = (UserInfoResponse) profileSteps.when_getUserInfo(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(pathUserSchema)
                .saveResponseObject(FilterTransactionResponse.class);
        profileSteps.then_verifyUserInfoResponse(userInfoResponse, USER_SIGN_IN);
    }

    @Test(description = "Get user info unsuccess", groups = "profile", dataProvider = "invalidNetwork")
    public void get_user_info_unsuccess(String network) {
        baseSteps.when_authLogin(USER_SIGN_IN);
        MultiMap params = new MultiValueMap();
        params.put("network", network);
        profileSteps.when_getUserInfo(params)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, APIErrorMessage.ACCOUNT_NOT_FOUND, APIErrorCode.ACCOUNT_NOT_FOUND);
    }
    @Test(description = "Get user info not access token", groups = "profile")
    public void get_user_info_not_access_token() {
        MultiMap params = new MultiValueMap();
        params.put("network", ProfileContants.NETWORK[0]);
        profileSteps.when_getUserInfo(params)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_UNAUTHORIZED, APIErrorMessage.ACCOUNT_NOT_UNAUTHORIZED, APIErrorCode.ACCOUNT_NOT_UNAUTHORIZED);
    }

    @DataProvider(name ="invalidNetwork")
    public Object[][] dataInvalidNetwork() {
        return new Object[][]{
                {"123"},
                {"abc"},
                {"   "},
                {"asset1c6t4elexwkpuzq08ssylhhmc78ahlz0sgw5a7y"},
                {"asset1c0vymmx0nysjaa8q5vah78jmuqyew3kjm48azr"}
        };
    }

}
