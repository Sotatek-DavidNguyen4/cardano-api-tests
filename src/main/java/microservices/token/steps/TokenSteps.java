package microservices.token.steps;

import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.common.constants.RequestParams;
import microservices.common.steps.BaseSteps;
import microservices.token.models.*;
import org.testng.Assert;
import util.AttributeStandard;
import util.SortListUtil;

import java.util.ArrayList;
import java.util.Map;

import static constants.DateFormats.DATE_FORMAT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TokenSteps extends BaseSteps {
    @Step("get list token")
    public TokenSteps getListTokens(Map<String, Object> paramsToken){
        sendGet(Endpoints.TokenApi.GET_LIST_TOKEN, paramsToken);
        return this;
    }
    @Step("verify that current page of token")
    public TokenSteps then_verifyCurrentPageResponse(TokensModel tokensModel, Map<String,Object> param){
        RequestParams requestParams = new RequestParams(param, 0, 20);
        assertThat(tokensModel.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        return this;
    }
    @Step("verify size of token")
    public TokenSteps then_verifySizeOfResponse(TokensModel tokensModel, Map<String,Object> param, int defaultSize){
        RequestParams requestParams = new RequestParams(param, 0, defaultSize);
        assertThat(tokensModel.getData().size())
                .as("Value of field 'size' is wrong")
                .isEqualTo(requestParams.getSize());
        return this;
    }
    @Step("verify sort of token")
    public TokenSteps then_verifySortOfResponse(TokensModel tokensModel, Map<String,Object> param){
        RequestParams requestParams = new RequestParams(param, 0, 20);
        if (requestParams.getSort()!=null) {
            boolean sorted = SortListUtil.isSortedByField(new ArrayList<>(tokensModel.getData()), requestParams.getSort());
            assertThat(sorted).as("Tokens is not sorted by inputted params").isEqualTo(true);
        }
        return this;
    }
    @Step("verify response of get list token")
    public TokenSteps verifyResponseListToken(ArrayList<TokenModel> data){
        for(TokenModel a : data){
            Assert.assertTrue(AttributeStandard.isValidDateFormat(a.getTime(), DATE_FORMAT[0]));
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
        Assert.assertTrue(AttributeStandard.isValidDateFormat(tokenModel.getTime(), DATE_FORMAT[0]));
        Assert.assertTrue(AttributeStandard.isValidTokenFingerprint(tokenModel.getFingerprint()));
        return this;
    }
    @Step("get token txs")
    public TokenSteps getTokenTxs(String tokenId, Map<String, Object> params){
        sendGet(Endpoints.TokenApi.GET_TXS, params, Endpoints.TokenApi.TOKEN_ID, tokenId);
        return this;
    }
    @Step("verify that current page of token txs")
    public TokenSteps then_verifyPageTokensTxsResponse(TokensTxsModel tokensTxsModel, Map<String,Object> param, int defaultSize){
        RequestParams requestParams = new RequestParams(param, 0, defaultSize);
        assertThat(tokensTxsModel.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        return this;
    }
    @Step("verify that size of token txs")
    public TokenSteps then_verifySizeTokensTxsResponse(TokensTxsModel tokensTxsModel, Map<String,Object> param, int defaultSize){
        RequestParams requestParams = new RequestParams(param, 0, defaultSize);
        assertThat(tokensTxsModel.getData().size())
                .as("Value of field 'size' is wrong")
                .isEqualTo(requestParams.getSize());
        return this;
    }
    @Step("verify sort of token txs")
    public TokenSteps then_verifySortTokenTxsOfResponse(TokensTxsModel tokensTxsModel, Map<String,Object> param){
        RequestParams requestParams = new RequestParams(param, 0, 20);
        if (requestParams.getSort()!=null) {
            boolean sorted = SortListUtil.isSortedByField(new ArrayList<>(tokensTxsModel.getData()), requestParams.getSort());
            assertThat(sorted).as("Token txs is not sorted by inputted params").isEqualTo(true);
        }
        return this;
    }
    @Step("verify response token txs")
    public TokenSteps verifyTokenTxs(ArrayList<TokenTxsModel> data){
        for(TokenTxsModel a : data){
            Assert.assertTrue(AttributeStandard.isValidDateFormat(a.getTime(), DATE_FORMAT[0]));
            Assert.assertTrue(AttributeStandard.isValidHash(a.getHash()));
            Assert.assertTrue(AttributeStandard.isValidBlockHash(a.getBlockHash()));
            Assert.assertTrue(a.getTotalOutput() instanceof Double || a.getTotalOutput() instanceof Float);
        }
        return this;
    }
    @Step("get token mints")
    public TokenSteps getTokenMint(String tokenId, Map<String, Object> param){
        sendGet(Endpoints.TokenApi.GET_MINTS, param, Endpoints.TokenApi.TOKEN_ID, tokenId);
        return this;
    }
    @Step("verify that current page of token mints")
    public TokenSteps then_verifyPageTokensMintsResponse(TokensMintsModel tokensMintsModel, Map<String,Object> param){
        RequestParams requestParams = new RequestParams(param, 0, 6);
        assertThat(tokensMintsModel.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        return this;
    }
    @Step("verify that size of token mints")
    public TokenSteps then_verifySizeTokensMintsResponse(TokensMintsModel tokensMintsModel, Map<String,Object> param, int defaultSize){
        RequestParams requestParams = new RequestParams(param, 0, defaultSize);
        assertThat(tokensMintsModel.getData().size())
                .as("Value of field 'size' is wrong")
                .isEqualTo(requestParams.getSize());
        return this;
    }
    @Step("verify that sort of token mints")
    public TokenSteps then_verifySortTokensMintsResponse(TokensMintsModel tokensMintsModel, Map<String,Object> param){
        RequestParams requestParams = new RequestParams(param, 0, 6);
        if (requestParams.getSort() != null) {
            boolean sorted = SortListUtil.isSortedByField(new ArrayList<>(tokensMintsModel.getData()), requestParams.getSort());
            assertThat(sorted).as("token mints is not sorted by inputted params").isEqualTo(true);
        }
        return this;
    }
    @Step("verify response token mints")
    public TokenSteps verifyTokenMints(ArrayList<TokenMintsModel> data){
        for(TokenMintsModel a : data){
            Assert.assertTrue(AttributeStandard.isValidDateFormat(a.getId(), DATE_FORMAT[0]));
            Assert.assertTrue(AttributeStandard.isValidHash(a.getTxHash()));
        }
        return this;
    }
    @Step("verify that response of token mint should be sort")
    public TokenSteps then_verifySortTokensMints(TokensMintsModel tokensMintsModel, Map<String, Object> param){
        RequestParams requestParams = new RequestParams(param, 0, 20);
        if (requestParams.getSort()!=null) {
            System.out.println("111111111111111111: "+requestParams.getSort());
            boolean sorted = SortListUtil.isSortedByField(new ArrayList<>(tokensMintsModel.getData()), requestParams.getSort());
            assertThat(sorted).as("Transaction is not sorted by inputted params").isEqualTo(true);
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
    public TokenSteps then_verifyPageTokensTopHoldersResponse(TokensTopHolderModel tokensTopHolderModel, Map<String,Object> param){
        RequestParams requestParams = new RequestParams(param, 0, 20);
        assertThat(tokensTopHolderModel.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        return this;
    }
    @Step("verify that size of token top holders")
    public TokenSteps then_verifySizeTokensTopHoldersResponse(TokensTopHolderModel tokensTopHolderModel, Map<String,Object> param, int defaultSize){
        RequestParams requestParams = new RequestParams(param, 0, defaultSize);
        assertThat(tokensTopHolderModel.getData().size())
                .as("Value of field 'size' is wrong")
                .isEqualTo(requestParams.getSize());
        return this;
    }
}
