package tests;

import base.BaseTest;
import microservices.token.models.DataToken;
import microservices.token.models.Token;
import microservices.token.steps.TokenSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Map;

public class TokenTests extends BaseTest {
    private TokenSteps tokenSteps = new TokenSteps();
    private Token token = new Token();
    private ArrayList<DataToken> dataTokens;
    @Test(description = "verify that get list token successfull", groups={"token"}, dataProvider = "paramSuccess")
    public void getListTokenSuccess(int page, int size, String sort){
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .withSort(sort)
                .getParamsMap();
        tokenSteps.getListTokens(param)
                .validateResponse(HttpURLConnection.HTTP_OK);
        token = tokenSteps.saveResponseListToken();
        dataTokens = token.getData();
        tokenSteps.verifyNumberPage(token.getCurrentPage(), page)
                .verifySizeOfResponse(dataTokens.size(), size);
    }
    @DataProvider(name="paramSuccess")
    public Object[][] dataSetSuccess(){
        return new Object[][]{
                {10,2,"supply,ASC"},
                {10,2, "txCount,DESC"},
                {-10,2, "supply,ASC"},
                {10,-10, "supply,ASC"}
        };
    }

    @Test(description = "verify that get list token with page invalid", groups = {"token"}, dataProvider = "paramInvalidPage")
    public void getListTokenWithPageInvalid(String page, int size, String sort){
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .withSort(sort)
                .getParamsMap();
        tokenSteps.getListTokensWithPageInvalid(param)
                .validateResponse(HttpURLConnection.HTTP_OK);
        token = tokenSteps.saveResponseListToken();
        dataTokens = token.getData();
        tokenSteps.verifyNumberPageInvalid(token.getCurrentPage())
                .verifySizeOfResponse(dataTokens.size(), size);
    }
    @DataProvider(name ="paramInvalidPage")
    public Object[][] dataSetInvalidPage(){
        return new Object[][]{
                {"abc", 5, "txCount,DESC"},
                {"  ", 5, "txCount,DESC"},
                {"@#$%%", 5, "txCount,DESC"},
        };
    }
    @Test(description = "verify that get list token with size invalid", groups = {"token"}, dataProvider = "paramInvalidSize")
    public void getListTokenWithSizeInvalid(int page, String size, String sort){
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .withSort(sort)
                .getParamsMap();
        tokenSteps.getListTokensWithSizeInvalid(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK);
        token = tokenSteps.saveResponseListToken();
        dataTokens = token.getData();
        tokenSteps.verifyNumberPage(token.getCurrentPage(), page)
                .verifySizeInvalidOfResponse(dataTokens.size());
    }
    @DataProvider(name = "paramInvalidSize")
    public Object[][] DataSwetInvalidSize(){
        return new Object[][]{
                {5,"  ", "txCount,ASC"},
                {5, "y", "txCount,ASC"},
                {5, "@#$", "txCount,ASC"},
        };
    }
    @Test(description = "verify that get list token with sort invalid", groups = {"token"}, dataProvider = "paramInvalidSort")
    public void getListTokenWithSortInvalid(int page, int size, String sort){
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .withSort(sort)
                .getParamsMap();
        tokenSteps.getListTokensWithSortInvalid(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK);
    }
    @DataProvider(name = "paramInvalidSort")
    public Object[][] DataSwetInvalidSort(){
        return new Object[][]{
                {10,2, "txCount,DESC"},
                {10,2, "txCount,ASC"},
                {10,2, "supply,ASC"},
                {10,2, "supply,DESC"}
        };
    }
    @Test(description = "verify that get a token with tokenId valid | success", groups = {"token"})
    public void getATokenSuccess(){
        tokenSteps.getAToken("asset1ee0u29k4xwauf0r7w8g30klgraxw0y4rz2t7xs")
                .validateStatusCode(HttpURLConnection.HTTP_OK);
    }
    @Test(description = "verify that get a token with tokenId invalid | fail", groups = {"token"}, dataProvider = "tokenIdInvalid")
    public void getATokenFail(String tokenId){
        tokenSteps.getAToken(tokenId)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST,"Token not found", "BC_404-TOKEN_NOT_FOUND");
    }
    @DataProvider(name = "tokenIdInvalid")
    public Object[][] DatasetWithTokenIdInvalid(){
        return new Object[][]{
                {"asset1ee0u29k4xwauf0r7w8g30klgraxw0y4rz2t"},
                {"@#$%=&"},
                {" "}
        };
    }
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
    @Test(description = "get token mints invalid param", groups = {"token"}, dataProvider = "paramTokenMintsParamInvalid")
    public void getTokenMintsInvalidParam(Object page, Object size, String sort){
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .withSort(sort)
                .getParamsMap();
        tokenSteps.getTokenTxsParamInvalid("asset1d9v7aptfvpx7we2la8f25kwprkj2ma5rp6uwzv", param)
                .validateResponse(HttpURLConnection.HTTP_OK);
    }
    @DataProvider(name = "paramTokenMintsParamInvalid")
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