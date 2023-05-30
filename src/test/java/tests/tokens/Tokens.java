package tests.tokens;

import base.BaseTest;
import microservices.token.models.TokenModel;
import microservices.token.models.TokensModel;
import microservices.token.steps.TokenSteps;
import org.apache.commons.collections.MultiMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateMultiParameters;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Map;

public class Tokens extends BaseTest {
    private TokenSteps tokenSteps = new TokenSteps();
    @Test(description = "verify that get list token successfull no key", groups={"token", "tokens"})
    public void getListTokenSuccessNokey(){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        TokensModel tokensModel = (TokensModel)
        tokenSteps.getListTokens(param)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TokensModel.class);
        tokenSteps.then_verifyFilterTokensResponse(tokensModel, param);
    }

    @Test(description = "verify that get list token with page key", groups = {"token","tokens"}, dataProvider = "paramInvalidPage")
    public void getListTokenWithPageKey(String page){
        MultiMap param = new CreateMultiParameters()
                .withPage(page)
                .getParamsMap();
        TokensModel tokensModel = (TokensModel)
                tokenSteps.getListTokens(param)
                        .validateResponse(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(TokensModel.class);
        tokenSteps.then_verifyFilterTokensResponse(tokensModel, param);
    }
    @DataProvider(name ="paramInvalidPage")
    public Object[][] dataSetInvalidPage(){
        return new Object[][]{
                {"10"},
                {"n"},
                {"-10"},
                {"  "},
                {"@#$"}
        };
    }
    @Test(description = "verify that get list token with size key", groups = {"token","tokens"}, dataProvider = "paramInvalidSize")
    public void getListTokenWithSizeKey(String size){
        MultiMap param = new CreateMultiParameters()
                .withPageSize(size)
                .getParamsMap();
        TokensModel tokensModel = (TokensModel)
                tokenSteps.getListTokens(param)
                        .validateResponse(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(TokensModel.class);
        tokenSteps.then_verifyFilterTokensResponse(tokensModel, param);
    }
    @DataProvider(name = "paramInvalidSize")
    public Object[][] DataSetInvalidSize(){
        return new Object[][]{
                {"1"},
                {"v"},
                {"-10"},
                {"  "},
                {"@#$"}
        };
    }
    @Test(description = "verify that get list token with sort key", groups = {"token","tokens"}, dataProvider = "paramInvalidSort")
    public void getListTokenWithSortKey(String sort){
        MultiMap param = new CreateMultiParameters()
                .withSort(sort)
                .getParamsMap();
        TokensModel tokensModel = (TokensModel)
                tokenSteps.getListTokens(param)
                        .validateResponse(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(TokensModel.class);
        tokenSteps.then_verifyFilterTokensResponse(tokensModel, param);
    }
    @DataProvider(name = "paramInvalidSort")
    public Object[][] DataSetInvalidSort(){
        return new Object[][]{
                {"txCount,DESC"},
                {"txCount,ASC"},
                {"supply,ASC"},
                {"supply,DESC"}
        };
    }
}
