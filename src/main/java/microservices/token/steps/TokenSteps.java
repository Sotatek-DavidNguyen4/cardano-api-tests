package microservices.token.steps;

import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;
import microservices.common.models.ErrorResponse;
import microservices.common.steps.BaseSteps;
import microservices.token.models.Token;
import org.testng.Assert;

import java.util.Map;

public class TokenSteps extends BaseSteps {
    private Token token = new Token();
    @Step("get list token")
    public TokenSteps getListTokens(Map<String, Object> paramsToken){
        sendGet(Endpoints.TokenApi.GET_LIST_TOKEN, paramsToken);
        return this;
    }
    @Step("save response get list token")
    public Token saveResponseListToken(){
        token = (Token) saveResponseObject(Token.class);
        return token;
    }
    @Step("verify number page of response get list token")
    public TokenSteps verifyNumberPage(int pageNumberActual, int pageNumberExpect){
        if(pageNumberExpect > 0){
            Assert.assertEquals(pageNumberActual, pageNumberExpect);
        }else{
            Assert.assertEquals(pageNumberActual, 0);
        }
        return this;
    }
    @Step("verify number size of response get list token")
    public TokenSteps verifySizeOfResponse(int sizeActual, int sizeExpect){
        if(sizeExpect < 0){
            Assert.assertEquals(sizeActual, 20);
        }else {
            Assert.assertEquals(sizeActual, sizeExpect);
        }
        return this;
    }
    @Step("get list token with page invalid")
    public TokenSteps getListTokensWithPageInvalid(Map<String, Object> paramsToken){
        sendGet(Endpoints.TokenApi.GET_LIST_TOKEN, paramsToken);
        return this;
    }
    @Step("verify number page invalid of response get list token")
    public TokenSteps verifyNumberPageInvalid(int pageNumberActual){
        Assert.assertEquals(pageNumberActual, 0);
        return this;
    }
    @Step("get list token with size invalid")
    public TokenSteps getListTokensWithSizeInvalid(Map<String, Object> paramsToken){
        sendGet(Endpoints.TokenApi.GET_LIST_TOKEN, paramsToken);
        return this;
    }
    @Step("verify number size valid of response get list token")
    public TokenSteps verifySizeInvalidOfResponse(int sizeActual){
        Assert.assertEquals(sizeActual, 20);
        return this;
    }
    @Step("get list token with sort invalid")
    public TokenSteps getListTokensWithSortInvalid(Map<String, Object> paramsToken){
        sendGet(Endpoints.TokenApi.GET_LIST_TOKEN, paramsToken);
        return this;
    }
    @Step("get a token")
    public TokenSteps getAToken(String tokenId){
        sendGet(Endpoints.TokenApi.GET_A_TOKEN, Endpoints.TokenApi.TOKEN_ID, tokenId);
        return this;
    }
    @Step("get token txs")
    public TokenSteps getTokenTxs(String tokenId){
        sendGet(Endpoints.TokenApi.GET_TXS, Endpoints.TokenApi.TOKEN_ID, tokenId);
        return this;
    }
    @Step("get token txs with params invalid")
    public TokenSteps getTokenTxsParamInvalid(String tokenId, Map<String, Object> param){
        sendGet(Endpoints.TokenApi.GET_TXS, param, Endpoints.TokenApi.TOKEN_ID, tokenId);
        return this;
    }
}
