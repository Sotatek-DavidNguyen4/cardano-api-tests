package microservices.token.steps;

import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.common.constants.RequestParams;
import microservices.common.steps.BaseSteps;
import microservices.token.models.*;
import org.testng.Assert;
import util.AttributeStandard;

import java.util.ArrayList;
import java.util.Map;

import static constants.DateFormats.DATE_FORMAT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.Assert.assertTrue;

public class TokenSteps extends BaseSteps {
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
    @Step("verify response of get list token")
    public TokenSteps verifyResponseListToken(ArrayList<TokenModel> data){
        for(TokenModel a : data){
            Assert.assertTrue(AttributeStandard.isValidDateFormat(a.getCreatedOn(), DATE_FORMAT[0]));
            Assert.assertTrue(AttributeStandard.isValidTokenFingerprint(a.getFingerprint()));
        }
        return this;
    }
    @Step("get list token with size invalid")
    public TokenSteps getListTokensWithSizeInvalid(Map<String, Object> paramsToken){
        sendGet(Endpoints.TokenApi.GET_LIST_TOKEN, paramsToken);
        return this;
    }
    @Step("get a token")
    public TokenSteps getAToken(Object tokenId){
        sendGet(Endpoints.TokenApi.GET_A_TOKEN, Endpoints.TokenApi.TOKEN_ID, tokenId);
        return this;
    }
    @Step("verify response of get a token")
    public TokenSteps verifyResponseOfGetToken(TokenModel tokenModel, Map<String, Object> expect){
        Assert.assertEquals(expect.get("name"), tokenModel.getName());
        Assert.assertEquals(expect.get("displayName"), tokenModel.getDisplayName());
        Assert.assertEquals(expect.get("policy"), tokenModel.getPolicy());
        Assert.assertTrue(AttributeStandard.isValidDateFormat(tokenModel.getCreatedOn(), DATE_FORMAT[0]));
        Assert.assertTrue(AttributeStandard.isValidTokenFingerprint(tokenModel.getFingerprint()));
        return this;
    }
    @Step("get token txs")
    public TokenSteps getTokenTxs(String tokenId, Map<String, Object> params){
        sendGet(Endpoints.TokenApi.GET_TXS, params, Endpoints.TokenApi.TOKEN_ID, tokenId);
        return this;
    }
    @Step("verify that current page of token txs")
    public TokenSteps then_verifyFilterTokensTxsResponse(TokensTxsModel tokensTxsModel, Map<String,Object> param, int defaultSize){
        RequestParams requestParams = new RequestParams(param, 0, defaultSize);
        assertThat(tokensTxsModel.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());

        assertThat(tokensTxsModel.getData().size())
                .as("Value of field 'size' is wrong")
                .isEqualTo(requestParams.getSize());
        return this;
    }
    @Step("verify response token txs")
    public TokenSteps verifyTokenTxs(ArrayList<TokenTxsModel> data){
        for(TokenTxsModel a : data){
            Assert.assertTrue(AttributeStandard.isValidDateFormat(a.getTime(), DATE_FORMAT[0]));
            Assert.assertTrue(AttributeStandard.isValidHash(a.getHash()));
            Assert.assertTrue(AttributeStandard.isValidBlockHash(a.getBlockHash()));
        }
        return this;
    }
    @Step("get token mints")
    public TokenSteps getTokenMint(String tokenId, Map<String, Object> param){
        sendGet(Endpoints.TokenApi.GET_MINTS, param, Endpoints.TokenApi.TOKEN_ID, tokenId);
        return this;
    }
    @Step("verify that current page of token mints")
    public TokenSteps then_verifyFilterTokensMintsResponse(TokensMintsModel tokensMintsModel, Map<String,Object> param, int defaultSize){
        RequestParams requestParams = new RequestParams(param, 0, defaultSize);
        assertThat(tokensMintsModel.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        assertThat(tokensMintsModel.getData().size())
                .as("Value of field 'size' is wrong")
                .isEqualTo(requestParams.getSize());
        return this;
    }
    @Step("verify response token mints")
    public TokenSteps verifyTokenMints(ArrayList<TokenMintsModel> data){
        for(TokenMintsModel a : data){
            Assert.assertTrue(AttributeStandard.isValidDateFormat(a.getTime(), DATE_FORMAT[0]));
            Assert.assertTrue(AttributeStandard.isValidHash(a.getTxHash()));
        }
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
    @Step("get token top holder")
    public TokenSteps getTokenTopHolder(String tokenId){
        sendGet(Endpoints.TokenApi.GET_TOP_HOLDERS, Endpoints.TokenApi.TOKEN_ID, tokenId );
        return this;
    }
    @Step("get token top holder param valid")
    public TokenSteps getTokenTopHoldersParamValid(Map<String, Object> param, String tokenId){
        sendGet(Endpoints.TokenApi.GET_TOP_HOLDERS, param, Endpoints.TokenApi.TOKEN_ID, tokenId );
        return this;
    }
    @Step("verify that current page of token top holders")
    public TokenSteps then_verifyFilterTokensTopHoldersResponse(TokensTopHolderModel tokensTopHolderModel, Map<String,Object> param, int defaultSize){
        RequestParams requestParams = new RequestParams(param, 0, defaultSize);
        assertThat(tokensTopHolderModel.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());

        assertThat(tokensTopHolderModel.getData().size())
                .as("Value of field 'size' is wrong")
                .isEqualTo(requestParams.getSize());
        return this;
    }
}
