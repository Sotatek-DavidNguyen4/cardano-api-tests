package tests.tokens;

import base.BaseTest;
import microservices.token.models.TokensTxsModel;
import microservices.token.steps.TokenSteps;
import org.apache.commons.collections.MultiMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateMultiParameters;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

public class TokenTxs extends BaseTest {
    private TokenSteps tokenSteps = new TokenSteps();
    private String tokenId = "asset1546zv6l65l26nwnf2vs4s4nyyr5x65uhpu9hud";
    @Test(description = "get token txs", groups = {"token", "token_txs"})
    public void getTokenTxs(){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        TokensTxsModel tokensTxsModel = (TokensTxsModel)
        tokenSteps.getTokenTxs(tokenId, param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TokensTxsModel.class);
        tokenSteps.then_verifyFilterTokensTxsResponse(tokensTxsModel, param, 20)
                .verifyTokenTxs(tokensTxsModel.getData());
    }
    @Test(description = "get token txs with page", groups = {"token", "token_txs"}, dataProvider = "tokenTxsPage")
    public void getTokenTxsPage(String page){
        MultiMap param = new CreateMultiParameters()
                .withPage(page)
                .getParamsMap();
        TokensTxsModel tokensTxsModel = (TokensTxsModel)
                tokenSteps.getTokenTxs(tokenId, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(TokensTxsModel.class);
        tokenSteps.then_verifyFilterTokensTxsResponse(tokensTxsModel, param, 20);
    }
    @DataProvider(name = "tokenTxsPage")
    public Object[][] DataSetTokenTxsPage(){
        return new Object[][]{
                {"10"},
                {"n"},
                {"-1"},
                {" "},
                {"@#$"}
        };
    }
    @Test(description = "get token txs with size", groups = {"token", "token_txs"}, dataProvider = "tokenTxsSize")
    public void getTokenTxsSize(String size){
        MultiMap param = new CreateMultiParameters()
                .withPage(size)
                .getParamsMap();
        TokensTxsModel tokensTxsModel = (TokensTxsModel)
                tokenSteps.getTokenTxs(tokenId, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(TokensTxsModel.class);
        tokenSteps.then_verifyFilterTokensTxsResponse(tokensTxsModel, param, 20);
    }
    @DataProvider(name = "tokenTxsSize")
    public Object[][] DataSetTokenTxsSize(){
        return new Object[][]{
                {"3"},
                {"v"},
                {"-3"},
                {" "},
                {"@#$"}
        };
    }
    @Test(description = "get token txs with tokenId invalid", groups = {"token", "token_txs"}, dataProvider = "tokenIdInvalid")
    public void getTokenTxsWithTokenIdInvalid(String token){
        MultiMap param = new CreateMultiParameters()
                .getParamsMap();
        TokensTxsModel tokensTxsModel = (TokensTxsModel)
                tokenSteps.getTokenTxs(token, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(TokensTxsModel.class);
//        tokenSteps.then_verifyFilterTokensTxsResponse(tokensTxsModel, param, 0);
    }
    @DataProvider(name = "tokenIdInvalid")
    public Object[][] DataSetTokenIdInvalid(){
        return new Object[][]{
                {"123"},
                {"@#$"},
                {"  "},
                {"asset1c6t4elexwkpuzq08ssylhhmc78ahlz0sgw5a7y"},
                {"asset1c0vymmx0nysjaa8q5vah78jmuqyew3kjm48azr"}
        };
    }
}
