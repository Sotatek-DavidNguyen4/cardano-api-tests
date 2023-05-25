package tests.tokens;

import base.BaseTest;
import microservices.token.steps.TokenSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

public class TokenMints extends BaseTest {
    private TokenSteps tokenSteps = new TokenSteps();
    @Test(description = "get token mints with param valid", groups = {"token"}, dataProvider = "tokenMintsParamValid")
    public void getTokenMints(Object page, Object size, String sort){
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .withSort(sort)
                .getParamsMap();
        tokenSteps.getTokenMintParamValid(param, "asset1998ard8xys6zatqmntlacgcwp6w52fuk52cynm")
                .validateStatusCode(HttpURLConnection.HTTP_OK);
    }
    @DataProvider(name = "tokenMintsParamValid")
    public Object[][] DatasetTokenMintsParamValid(){
        return new Object[][]{
                {1, 5, "id,ASC"},
                {1, 5, "id,DESC"},
        };
    }
    @Test(description = "get token mints invalid param", groups = {"token"}, dataProvider = "tokenMintsParamInvalid")
    public void getTokenMintsInvalidParam(Object page, Object size, String sort){
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .withSort(sort)
                .getParamsMap();
        tokenSteps.getTokenTxsParamInvalid("asset1d9v7aptfvpx7we2la8f25kwprkj2ma5rp6uwzv", param)
                .validateResponse(HttpURLConnection.HTTP_OK);
    }
    @DataProvider(name = "tokenMintsParamInvalid")
    public Object[][] DatasetTokenMintsParamInvalid(){
        return new Object[][]{
                // data for page invalid
                {" ", 5, "id,ASC"},
                {"", 5, "id,ASC"},
                {null, 5, "id,ASC"},
                {"abc", 5, "id,ASC"},
                {"@#$", 5, "id,ASC"},

                {" ", 5, "id,DESC"},
                {"", 5, "id,DESC"},
                {null, 5, "id,DESC"},
                {"abc", 5, "id,DESC"},
                {"@#$", 5, "id,DESC"},
                //data for size invalid
                {0, null, "id,ASC"},
                {0, "", "id,ASC"},
                {0, " ", "id,ASC"},
                {0, "abc", "id,ASC"},
                {0, "@#$", "id,ASC"},

                {0, null, "id,DESC"},
                {0, "", "id,DESC"},
                {0, " ", "id,DESC"},
                {0, "abc", "id,DESC"},
                {0, "@#$", "id,DESC"},
                //data for sort invalid
                {1, 5, ""},
                {1, 5, " "},
                {1, 5, null},
                {1, 5, "abc"},
                {1, 5, "@#$"}
        };
    }
}
