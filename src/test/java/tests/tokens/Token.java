package tests.tokens;

import base.BaseTest;
import microservices.token.constants.TokenConstants;
import microservices.token.steps.TokenSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;

public class Token extends BaseTest {
    private TokenSteps tokenSteps = new TokenSteps();
    private String token = "asset1ee0u29k4xwauf0r7w8g30klgraxw0y4rz2t7xs";
    @Test(description = "verify that get a token with tokenId valid | success", groups = {"token"})
    public void getATokenSuccess(){
        tokenSteps.getAToken(token)
                .validateStatusCode(HttpURLConnection.HTTP_OK);
    }
    @Test(description = "verify that get a token with tokenId invalid | fail", groups = {"token"}, dataProvider = "tokenIdInvalid")
    public void getATokenFail(String tokenId){
        tokenSteps.getAToken(tokenId)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, TokenConstants.ERROR_MESSAGE, TokenConstants.ERROR_CODE);
    }
    @DataProvider(name = "tokenIdInvalid")
    public Object[][] DatasetWithTokenIdInvalid(){
        return new Object[][]{
                {"asset1ee0u29k4xwauf0r7w8g30klgraxw0y4rz2t"},
                {"@#$"},
                {" "}
        };
    }
}
