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
    private String tokenId = "asset17q7r59zlc3dgw0venc80pdv566q6yguw03f0d9";
    @Test(description = "get token top holder", groups = {"token", "top_holder"})
    public void getTokenTopHolder(){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        TokensTopHolderModel tokensTopHolderModel = (TokensTopHolderModel)
        tokenSteps.getTokenTopHolder(tokenId)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TokensTopHolderModel.class);
        tokenSteps.then_verifyPageTokensTopHoldersResponse(tokensTopHolderModel, param)
                .then_verifySizeTokensTopHoldersResponse(tokensTopHolderModel, param, 20);
    }
//    @Test(description = "get token top holders with invalid tokenId", groups = {"token", "top_holder"}, dataProvider = "invalidTokenId")
//    public void getTokenTopHolderWithInvalidTokenId(String tokenId){
//        MultiMap param = new CreateMultiParameters()
//                .getParamsMap();
//        TokensTopHolderModel tokensTopHolderModel = (TokensTopHolderModel)
//                tokenSteps.getTokenTopHoldersParamValid(param, tokenId)
//                        .validateStatusCode(HttpURLConnection.HTTP_OK)
//                        .saveResponseObject(TokensTopHolderModel.class);
//        tokenSteps.then_verifyPageTokensTopHoldersResponse(tokensTopHolderModel, param);
//    }
//    @DataProvider(name="invalidTokenId")
//    public Object[][] dataSetTokenId(){
//        return new Object[][]{
//                {"123"},
//                {"@#$"},
//                {"   "},
//                {"asset1c6t4elexwkpuzq08ssylhhmc78ahlz0sgw5a7y"},
//                {"asset1c0vymmx0nysjaa8q5vah78jmuqyew3kjm48azr"},
//        };
//    }
    @Test(description = "get token top holders with size", groups = {"token", "top_holder"}, dataProvider = "size")
    public void getTokenTopHolderWithSize(Object size){
        MultiMap param = new CreateMultiParameters()
                .withPageSize(size)
                .getParamsMap();
        TokensTopHolderModel tokensTopHolderModel = (TokensTopHolderModel)
                tokenSteps.getTokenTopHoldersParamValid(param, tokenId)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(TokensTopHolderModel.class);
        tokenSteps.then_verifyPageTokensTopHoldersResponse(tokensTopHolderModel, param)
                .then_verifySizeTokensTopHoldersResponse(tokensTopHolderModel, param, 20);
    }
    @DataProvider(name="size")
    public Object[][] dataSetSize(){
        return new Object[][]{
                {"2"},
                {"v"},
                {"-2"},
                {"!@@$$"},
                {"  "},
        };
    }
}
