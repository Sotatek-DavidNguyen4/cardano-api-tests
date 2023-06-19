package tests.policies.tokens;

import base.BaseTest;
import microservices.token.models.TokensMintsModel;
import microservices.token.steps.TokenSteps;
import org.apache.commons.collections.MultiMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateMultiParameters;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

public class TokenMints extends BaseTest {
    private TokenSteps tokenSteps = new TokenSteps();
    private String tokenId = "asset17q7r59zlc3dgw0venc80pdv566q6yguw03f0d9";
    private int numberPage;
    @Test(description = "get token mints", groups = {"token", "token_mints"}, priority = 0)
    public void getTokenMints(){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        TokensMintsModel tokensMintsModel = (TokensMintsModel)
        tokenSteps.getTokenMint(tokenId, param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TokensMintsModel.class);
        numberPage = tokensMintsModel.getTotalItems()/20 + 1;
        tokenSteps.then_verifyPageTokensMintsResponse(tokensMintsModel, param)
                .then_verifySizeTokensMintsResponse(tokensMintsModel, param, 6)
                .verifyTokenMints(tokensMintsModel.getData());
    }
    @Test(description = "get token mints invalid tokenId", groups = {"token", "token_mints"}, dataProvider = "tokenInvalid")
    public void getTokenMintsInvalidTokenId(String token){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        TokensMintsModel tokensMintsModel = (TokensMintsModel)
                tokenSteps.getTokenMint(token, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(TokensMintsModel.class);
        tokenSteps.then_verifyPageTokensMintsResponse(tokensMintsModel, param)
                .then_verifySizeTokensMintsResponse(tokensMintsModel, param, 0);
    }
    @DataProvider (name = "tokenInvalid")
    public Object[][] DatasetTokenIdInvalid() {
        return new Object[][]{
                {"@#$"},
                {" "},
                {"abc"},
                {"12345"}
        };
    }
    @Test(description = "get token mints with page", groups = {"token", "token_mints"}, dataProvider = "tokenMintPage")
    public void getTokenMintPage(String page){
        MultiMap param = new CreateMultiParameters()
                .withPage(page)
                .getParamsMap();
        TokensMintsModel tokensMintsModel = (TokensMintsModel)
                tokenSteps.getTokenMint(tokenId, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(TokensMintsModel.class);
        tokenSteps.then_verifyPageTokensMintsResponse(tokensMintsModel, param)
                .then_verifySizeTokensMintsResponse(tokensMintsModel, param, 6);
    }
    @DataProvider(name = "tokenMintPage")
    public Object[][] DataSetTokenMintPage(){
        return new Object[][]{
                {"abc"},
                {"-1"},
                {" "},
                {"@#$"}
        };
    }
    @Test(description = "get token mints with page = 2", groups = {"token", "token_mints"})
    public void getTokenMintPage2(){
        MultiMap param = new CreateMultiParameters()
                .withPage("2")
                .getParamsMap();
        TokensMintsModel tokensMintsModel = (TokensMintsModel)
                tokenSteps.getTokenMint(tokenId, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(TokensMintsModel.class);
        tokenSteps.then_verifyPageTokensMintsResponse(tokensMintsModel, param)
                .then_verifySizeTokensMintsResponse(tokensMintsModel, param, 0);
    }
    @Test(description = "get token mints with page = totalPage + 1", groups = {"token", "token_mints"}, priority = 1)
    public void getTokenMintPage(){
        MultiMap param = new CreateMultiParameters()
                .withPage(""+numberPage+"")
                .getParamsMap();
        TokensMintsModel tokensMintsModel = (TokensMintsModel)
                tokenSteps.getTokenMint(tokenId, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(TokensMintsModel.class);
        tokenSteps.then_verifyPageTokensMintsResponse(tokensMintsModel, param)
                .then_verifySizeTokensMintsResponse(tokensMintsModel, param, 0);
    }
    @Test(description = "get token mint with size", groups = {"token", "token_mints"}, dataProvider = "tokenMintSize")
    public void getTokenMintSize(String size){
        MultiMap param = new CreateMultiParameters()
                .withPageSize(size)
                .getParamsMap();
        TokensMintsModel tokensMintsModel = (TokensMintsModel)
                tokenSteps.getTokenMint(tokenId, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(TokensMintsModel.class);
        tokenSteps.then_verifyPageTokensMintsResponse(tokensMintsModel, param)
                .then_verifySizeTokensMintsResponse(tokensMintsModel, param, 6);
    }
    @DataProvider(name = "tokenMintSize")
    public Object[][] DataSetTokenMintSize(){
        return new Object[][]{
                {"2"},
                {"abc"},
                {"-2"},
                {" "},
                {"!@@$$"}
        };
    }
    @Test(description = "get token mint with sort", groups = {"token", "token_mints"}, dataProvider = "tokenMintSort")
    public void getTokenMintSort(String sort){
        MultiMap param = new CreateMultiParameters()
                .withSort(sort)
                .getParamsMap();
        TokensMintsModel tokensMintsModel = (TokensMintsModel)
                tokenSteps.getTokenMint(tokenId, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(TokensMintsModel.class);
        tokenSteps.then_verifySortTokensMintsResponse(tokensMintsModel, param)
                .then_verifySizeTokensMintsResponse(tokensMintsModel, param, 6)
                .then_verifyPageTokensMintsResponse(tokensMintsModel, param);
    }
    @DataProvider(name = "tokenMintSort")
    public Object[][] DataSetTokenMintSort(){
        return new Object[][]{
                {"id,DESC"},
                {"id,ASC"},
        };
    }
}
