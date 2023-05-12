package microservices.token.steps;

import com.fasterxml.jackson.databind.ser.Serializers;
import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;
import microservices.token.models.Token;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TokenSteps extends BaseApi {
    private BaseApi baseApi = new BaseApi();
    private Token token = new Token();
    @Step
    public TokenSteps getListTokens(int page, int size, ArrayList<String> sort){
        Map<String, Object> paramsToken = new HashMap<>();
        paramsToken.put("page", page);
        paramsToken.put("size", size);
        paramsToken.put("sort", sort.get(0));
        paramsToken.put("sort", sort.get(1));
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
    @Step("verify status response of get list token")
    public TokenSteps verifyResponseGetListToken(int statusCode){
        baseApi.validateResponse(statusCode);
        return this;
    }
    @Step("get list token with page invalid")
    public TokenSteps getListTokensWithPageInvalid(String page, int size, ArrayList<String> sort){
        Map<String, Object> paramsToken = new HashMap<>();
        paramsToken.put("page", page);
        paramsToken.put("size", size);
        paramsToken.put("sort", sort.get(0));
        paramsToken.put("sort", sort.get(1));
        sendGet(Endpoints.TokenApi.GET_LIST_TOKEN, paramsToken);
        return this;
    }
    @Step("verify number page invalid of response get list token")
    public TokenSteps verifyNumberPageInvalid(int pageNumberActual){
        Assert.assertEquals(pageNumberActual, 0);
        return this;
    }
    @Step()
    public TokenSteps getListTokensWithSizeInvalid(int page, String size, ArrayList<String> sort){
        Map<String, Object> paramsToken = new HashMap<>();
        paramsToken.put("page", page);
        paramsToken.put("size", size);
        paramsToken.put("sort", sort.get(0));
        paramsToken.put("sort", sort.get(1));
        sendGet(Endpoints.TokenApi.GET_LIST_TOKEN, paramsToken);
        return this;
    }
    @Step("verify number size valid of response get list token")
    public TokenSteps verifySizeInvalidOfResponse(int sizeActual){
        Assert.assertEquals(sizeActual, 20);
        return this;
    }
    @Step()
    public TokenSteps getListTokensWithSortInvalid(int page, int size, ArrayList<String> sort){
        Map<String, Object> paramsToken = new HashMap<>();
        paramsToken.put("page", page);
        paramsToken.put("size", size);
        paramsToken.put("sort", sort);
        sendGet(Endpoints.TokenApi.GET_LIST_TOKEN, paramsToken);
        return this;
    }
    @Step()
    public TokenSteps getAToken(String tokenId){
        sendGet(Endpoints.TokenApi.GET_A_TOKEN, "tokenId", tokenId);
        return this;
    }
}
