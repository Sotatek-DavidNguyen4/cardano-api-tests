package tests.tokens;

import base.BaseTest;
import microservices.token.steps.TokenSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

public class TokenTxs extends BaseTest {
    private TokenSteps tokenSteps = new TokenSteps();

    @Test(description = "get token txs valid param", groups = {"token"})
    public void getTokenTxs(){
        tokenSteps.getTokenTxs("asset1d9v7aptfvpx7we2la8f25kwprkj2ma5rp6uwzv")
                .validateStatusCode(HttpURLConnection.HTTP_OK);
    }
    @Test(description = "get token txs invalid param", groups = {"token"}, dataProvider = "paramTokenTxsParamInvalid")
    public void getTokenTxsInvalidParam(Object page, Object size){
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .getParamsMap();
        tokenSteps.getTokenTxsParamInvalid("asset1d9v7aptfvpx7we2la8f25kwprkj2ma5rp6uwzv", param)
                .validateResponse(HttpURLConnection.HTTP_OK);
    }
    @DataProvider(name = "paramTokenTxsParamInvalid")
    public Object[][] DatasetTokenTxsParamInvalid(){
        return new Object[][]{
                // data for page invalid
                {" ", 5},
                {"", 5},
                {null, 5},
                {"abc", 5},
                {"@#$", 5},
                //data for size invalid
                {0, null},
                {0, ""},
                {0, " "},
                {0, "abc"},
                {0, "@#$"},
        };
    }
}
