package tests.tokens;

import base.BaseTest;
import microservices.token.models.TokensTopHolderModel;
import microservices.token.steps.TokenSteps;
import org.apache.commons.collections.MultiMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateMultiParameters;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

public class TokenTopHolder extends BaseTest {
    private TokenSteps tokenSteps = new TokenSteps();
    private String tokenId = "asset1546zv6l65l26nwnf2vs4s4nyyr5x65uhpu9hud";
    @Test(description = "get token top holder", groups = {"token", "top_holder"})
    public void getTokenTopHolder(){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        TokensTopHolderModel tokensTopHolderModel = (TokensTopHolderModel)
        tokenSteps.getTokenTopHolder(tokenId)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TokensTopHolderModel.class);
        tokenSteps.then_verifyFilterTokensTopHoldersResponse(tokensTopHolderModel, param, 10);
    }
    @Test(description = "get token top holders with page", groups = {"token", "top_holder"}, dataProvider = "page")
    public void getTokenTopHolderWithPage(Object page){
        MultiMap param = new CreateMultiParameters()
                .withPage(page)
                .getParamsMap();
        TokensTopHolderModel tokensTopHolderModel = (TokensTopHolderModel)
                tokenSteps.getTokenTopHoldersParamValid(param, tokenId)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(TokensTopHolderModel.class);
        tokenSteps.then_verifyFilterTokensTopHoldersResponse(tokensTopHolderModel, param, 10);
    }
    @DataProvider(name="page")
    public Object[][] dataSetPage(){
        return new Object[][]{
                {"0"},
                {"n"},
                {"-1"},
                {"@#$"},
                {"  "},
        };
    }
}
