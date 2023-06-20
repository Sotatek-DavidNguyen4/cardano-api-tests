package tests.tokens;

import base.BaseTest;
import microservices.token.constants.TokenConstants;
import microservices.token.models.TokenModel;
import microservices.token.steps.TokenSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class Token extends BaseTest {
    private TokenSteps tokenSteps = new TokenSteps();
    private String token = "asset17q7r59zlc3dgw0venc80pdv566q6yguw03f0d9";
    @Test(description = "verify that get a token", groups = {"token", "token_tokenId"})
    public void getATokenSuccess(){
        Map<String, Object> expect = new HashMap<>();
        expect.put("name", "484f534b59");
        expect.put("displayName", "HOSKY");
        expect.put("policy", "a0028f350aaabe0545fdcb56b039bfb08e4bb4d8c4d7c3c7d481c235");
        expect.put("fingerprint", "asset17q7r59zlc3dgw0venc80pdv566q6yguw03f0d9");
        TokenModel tokenModel = (TokenModel)
        tokenSteps.getAToken(token)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TokenModel.class);
        tokenSteps.verifyResponseOfGetToken(tokenModel, expect);
    }
    @Test(description = "verify that get a token with tokenId invalid", groups = {"token", "token_tokenId"}, dataProvider = "tokenIdInvalid")
    public void getATokenFail(Object tokenId){
        tokenSteps.getAToken(tokenId)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, TokenConstants.ERROR_MESSAGE, TokenConstants.ERROR_CODE);
    }
    @DataProvider(name = "tokenIdInvalid")
    public Object[][] DatasetWithTokenIdInvalid(){
        return new Object[][]{
                {1},
                {"@#$"},
                {"  "},
                {"asset1c6t4elexwkpuzq08ssylhhmc78ahlz0sgw5a7y"},
                {"asset1c0vymmx0nysjaa8q5vah78jmuqyew3kjm48azr"}
        };
    }
}
