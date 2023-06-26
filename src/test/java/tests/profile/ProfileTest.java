package tests.profile;

import base.BaseTest;
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
import microservices.profile.models.*;
import microservices.profile.steps.ProfileSteps;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .saveResponseObject(UserInfoResponse.class);
        profileSteps.then_verifyUserInfoResponse(userInfoResponse, USER_SIGN_IN);

        // network = PRE_PROD
        params = new MultiValueMap();
        params.put("network", ProfileContants.NETWORK[1]);
        userInfoResponse = (UserInfoResponse) profileSteps.when_getUserInfo(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(pathUserSchema)
                .saveResponseObject(UserInfoResponse.class);
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

    @Test(description = "Get book mark success", groups = "profile")
    public void get_book_mark_success() {
        // network = MAIN_NET
        baseSteps.when_authLogin(USER_SIGN_IN);
        String pathBookMarkSchema = "schemaJson/profile/bookmarkInfo.json";
        MultiMap params = new MultiValueMap();
        params.put("network", ProfileContants.NETWORK[0]);
        List<BookmarkResponse> bookmarkResponseList = profileSteps.when_getBookmarkFillAllKey(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(pathBookMarkSchema)
                .saveResponseListObject(BookmarkResponse[].class);

        // network = PRE_PROD
        params = new MultiValueMap();
        params.put("network", ProfileContants.NETWORK[1]);
        bookmarkResponseList = profileSteps.when_getBookmarkFillAllKey(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(pathBookMarkSchema)
                .saveResponseListObject(BookmarkResponse[].class);
    }

    @Test(description = "Get book mark unsuccess", groups = "profile", dataProvider = "invalidNetwork")
    public void get_book_mark_unsuccess(String network) {
        baseSteps.when_authLogin(USER_SIGN_IN);
        MultiMap params = new MultiValueMap();
        params.put("network", network);
        profileSteps.when_getBookmarkFillAllKey(params)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, APIErrorMessage.ACCOUNT_NOT_FOUND, APIErrorCode.ACCOUNT_NOT_FOUND);
    }

    @Test(description = "Get book mark not access token", groups = "profile")
    public void get_book_mark_not_access_token() {
        MultiMap params = new MultiValueMap();
        params.put("network", ProfileContants.NETWORK[0]);
        profileSteps.when_getBookmarkFillAllKey(params)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_UNAUTHORIZED, APIErrorMessage.ACCOUNT_NOT_UNAUTHORIZED, APIErrorCode.ACCOUNT_NOT_UNAUTHORIZED);
    }

    @Test(description = "Get note success", groups = "profile")
    public void get_note_success() {
        // The account have record note
        baseSteps.when_authLogin(USER_SIGN_IN);
        String pathNoteSchema = "schemaJson/profile/noteInfo.json";
        String network = ProfileContants.NETWORK[0];

        CreateNoteInput createNoteInput = new CreateNoteInput(network);
        profileSteps.given_createNewNote(createNoteInput)
                .validateResponse(HttpURLConnection.HTTP_OK);

        Map<String, Object> params = new HashMap<>();
        params.put("network", network);
        profileSteps.when_getNoteFillAll(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .getJsonAsString().isEmpty();

        params.put("page", "");
        params.put("size", "");
        NoteResponse noteResponse = (NoteResponse) profileSteps.when_getNoteFillAll(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(pathNoteSchema)
                .saveResponseObject(NoteResponse.class);
        profileSteps.then_verifyCreatedNote(createNoteInput, noteResponse.getData().get(0), params);

        // The account hasn't any record note
        for (NoteResponse.Note note : noteResponse.getData()){
            profileSteps.when_deleteNote(note.getId())
                    .validateResponse(HttpURLConnection.HTTP_OK);
        }
        params = new HashMap<>();
        params.put("network", network);
        profileSteps.when_getNoteFillAll(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .getJsonAsString().isEmpty();

        params.put("page", "");
        params.put("size", "");
        noteResponse = (NoteResponse) profileSteps.when_getNoteFillAll(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(NoteResponse.class);
        profileSteps.then_verifyHasNotNote(noteResponse);
    }

    @Test(description = "Get note unsuccess", groups = "profile", dataProvider = "invalidNetwork")
    public void get_note_unsuccess(String network) {
        baseSteps.when_authLogin(USER_SIGN_IN);
        MultiMap params = new MultiValueMap();
        params.put("network", network);
        profileSteps.when_getNoteFillAll(params)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, APIErrorMessage.ACCOUNT_NOT_FOUND, APIErrorCode.ACCOUNT_NOT_FOUND);
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
