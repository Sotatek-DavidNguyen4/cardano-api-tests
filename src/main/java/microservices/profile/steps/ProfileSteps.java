package microservices.profile.steps;

import constants.Endpoints;
import data.DataUser;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import microservices.common.steps.BaseSteps;
import microservices.profile.models.CreateNoteInput;
import microservices.profile.models.NoteResponse;
import microservices.profile.models.UserInfoResponse;
import org.testng.Assert;

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
        System.out.println(getResponse().getBody().print());
        return this;
    }

    @Step("Verify user info response")
    public ProfileSteps then_verifyUserInfoResponse(UserInfoResponse userInfoResponse, DataUser user) {
        assertTrue(userInfoResponse.getEmail().equals(user.getEmail()));
        return this;
    }

    @Step("Create new note")
    public ProfileSteps given_createNewNote(CreateNoteInput createNoteInput) {
        sendPost(Endpoints.AccountsApi.CREATE_NOTE, createNoteInput);
        return this;
    }

    @Step("Verify created note response")
    public ProfileSteps then_verifyCreatedNote(CreateNoteInput createNoteInput, NoteResponse.Note noteResponse, Map<String, Object> params){
        Assert.assertEquals(createNoteInput.getTxHash(), noteResponse.getTxHash());
        Assert.assertEquals(createNoteInput.getNote(), noteResponse.getNote());
        Assert.assertEquals(createNoteInput.getNetwork(), params.get("network"));
        return this;
    }

    @Step("Create new note")
    public ProfileSteps when_deleteNote(String nodeId) {
        sendDelete(Endpoints.AccountsApi.DELETE_NOTE, Endpoints.AccountsApi.NOTE_ID, nodeId);
        return this;
    }

    @Step("Verify has not any note")
    public ProfileSteps then_verifyHasNotNote(NoteResponse noteResponse) {
        Assert.assertEquals(noteResponse.getData(), null);
        Assert.assertEquals(noteResponse.getTotalItems(), 0);
        return this;
    }

}
