package tests.tokens;

import base.BaseTest;
import microservices.token.steps.TokenSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;

public class Token extends BaseTest {
    private TokenSteps tokenSteps = new TokenSteps();
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
//                {"@#$%=&"},
                {" "}
        };
    }
}
