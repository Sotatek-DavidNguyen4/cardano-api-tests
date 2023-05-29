package microservices.token.steps;

import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.common.constants.RequestParams;
import microservices.common.steps.BaseSteps;
import microservices.token.models.TokenMintsModel;
import microservices.token.models.TokensMintsModel;
import microservices.token.models.TokensModel;
import microservices.token.models.TokensTxsModel;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TokenSteps extends BaseSteps {
    private TokensModel token = new TokensModel();
    @Step("get list token")
    public TokenSteps getListTokens(Map<String, Object> paramsToken){
        sendGet(Endpoints.TokenApi.GET_LIST_TOKEN, paramsToken);
        return this;
    }
    @Step("verify that current page of token")
    public TokenSteps then_verifyFilterTokensResponse(TokensModel tokensModel, Map<String,Object> param){
        RequestParams requestParams = new RequestParams(param, 0, 20);
        assertThat(tokensModel.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        assertThat(tokensModel.getData().size())
                .as("Value of field 'size' is wrong")
                .isEqualTo(requestParams.getSize());
        return this;
    }
    @Step("save response get list token")
    public TokensModel saveResponseListToken(){
        token = (TokensModel) saveResponseObject(TokensModel.class);
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
    public TokenSteps getAToken(Object tokenId){
        sendGet(Endpoints.TokenApi.GET_A_TOKEN, Endpoints.TokenApi.TOKEN_ID, tokenId);
        return this;
    }
    @Step("get token txs")
    public TokenSteps getTokenTxs(String tokenId, Map<String, Object> params){
        sendGet(Endpoints.TokenApi.GET_TXS, params, Endpoints.TokenApi.TOKEN_ID, tokenId);
        return this;
    }
    @Step("verify that current page of token txs")
    public TokenSteps then_verifyFilterTokensMintsResponse(TokensTxsModel tokensTxsModel, Map<String,Object> param){
        RequestParams requestParams = new RequestParams(param, 0, 20);
        assertThat(tokensTxsModel.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        assertThat(tokensTxsModel.getData().size())
                .as("Value of field 'size' is wrong")
                .isEqualTo(requestParams.getSize());
        return this;
    }
    @Step("get token txs with params invalid")
    public TokenSteps getTokenTxsParamInvalid(String tokenId, Map<String, Object> param){
        sendGet(Endpoints.TokenApi.GET_TXS, param, Endpoints.TokenApi.TOKEN_ID, tokenId);
        return this;
    }
    @Step("get token mints")
    public TokenSteps getTokenMint(String tokenId, Map<String, Object> param){
        sendGet(Endpoints.TokenApi.GET_MINTS, param, Endpoints.TokenApi.TOKEN_ID, tokenId);
        return this;
    }
    @Step("verify that current page of token mints")
    public TokenSteps then_verifyFilterTokensMintsResponse(TokensMintsModel tokensMintsModel, Map<String,Object> param){
        RequestParams requestParams = new RequestParams(param, 0, 20);
        assertThat(tokensMintsModel.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        assertThat(tokensMintsModel.getData().size())
                .as("Value of field 'size' is wrong")
                .isEqualTo(requestParams.getSize());
        return this;
    }
    @Step("verify that response of token mint should be sort")
    public TokenSteps then_verifySortTokensMints(TokensMintsModel tokensMintsModel, Map<String, Object> param){
        if(param.get("sort").equals("id,DESC")){
            ArrayList<TokenMintsModel> data = tokensMintsModel.getData();
            for(int i=0; i<data.size(); i++){
            }
        }
        return this;
    }
}
