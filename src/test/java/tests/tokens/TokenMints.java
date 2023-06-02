package tests.tokens;

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
    private String tokenId = "asset1dcspl93vqst7k7fcz2vx4mu6jvq7hsrse7zlpv";
    @Test(description = "get token mints", groups = {"token", "token_mints"})
    public void getTokenMints(){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        TokensMintsModel tokensMintsModel = (TokensMintsModel)
        tokenSteps.getTokenMint(tokenId, param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TokensMintsModel.class);
        tokenSteps.then_verifyFilterTokensMintsResponse(tokensMintsModel, param, 20)
                .verifyTokenMints(tokensMintsModel.getData());
    }
    @Test(description = "get token mints all key", groups = {"token", "token_mints"})
    public void getTokenMintsAllKey(){
        MultiMap param = new CreateMultiParameters()
                .withPage("0")
                .withPageSize("20")
                .withPageSize("id,DESC")
                .getParamsMap();
        TokensMintsModel tokensMintsModel = (TokensMintsModel)
                tokenSteps.getTokenMint(tokenId, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(TokensMintsModel.class);
        tokenSteps.then_verifyFilterTokensMintsResponse(tokensMintsModel, param, 20);
    }
    @Test(description = "get token mints invalid tokenId", groups = {"token", "token_mints"}, dataProvider = "tokenInvalid")
    public void getTokenMintsInvalidTokenId(String token){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        TokensMintsModel tokensMintsModel = (TokensMintsModel)
                tokenSteps.getTokenMint(token, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(TokensMintsModel.class);
        tokenSteps.then_verifyFilterTokensMintsResponse(tokensMintsModel, param, 0);
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
        tokenSteps.then_verifyFilterTokensMintsResponse(tokensMintsModel, param, 20);
    }
    @DataProvider(name = "tokenMintPage")
    public Object[][] DataSetTokenMintPage(){
        return new Object[][]{
                {"10"},
                {"abc"},
                {"-10"},
                {" "},
                {"@#$"}
        };
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
        tokenSteps.then_verifyFilterTokensMintsResponse(tokensMintsModel, param, 20);
    }
    @DataProvider(name = "tokenMintSize")
    public Object[][] DataSetTokenMintSize(){
        return new Object[][]{
                {"1"},
                {"abc"},
                {"-10"},
                {" "},
                {"@#$$"}
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
        tokenSteps.then_verifyFilterTokensMintsResponse(tokensMintsModel, param, 20);
    }
    @DataProvider(name = "tokenMintSort")
    public Object[][] DataSetTokenMintSort(){
        return new Object[][]{
                {"id,DESC"},
                {"id,ASC"},
        };
    }
}
