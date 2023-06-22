package tests.tokens;

import base.BaseTest;
import microservices.token.constants.TokenConstants;
import microservices.token.models.TokenAnalytics;
import microservices.token.steps.TokenSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class TokenAnalyticType extends BaseTest {
    private TokenSteps tokenSteps = new TokenSteps();
    private String tokenId = "asset17q7r59zlc3dgw0venc80pdv566q6yguw03f0d9";
    @Test(description = "get token analytics type", groups = {"token", "token_analytic_type"}, dataProvider = "type")
    public void getTokenAnalyticType(String type){
        String pathTokenAnalyticsSchema = "schemaJson/tokens/tokenAnalytics.json";
        List<TokenAnalytics> ananlytics = tokenSteps.getTokenAnalyticsType(tokenId, type)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseListObject(TokenAnalytics[].class);
        tokenSteps.verifyResponseTokenAnalyticsType(ananlytics, type)
                .validateResponseSchema(pathTokenAnalyticsSchema);
    }
    @DataProvider(name = "type")
    public Object[][] DataSetType(){
        return new Object[][]{
                {"ONE_DAY"},
                {"ONE_WEEK"},
                {"ONE_MONTH"},
                {"THREE_MONTH"}
        };
    }
    @Test(description = "get token analytics type tokenId = @#$", groups = {"token", "token_analytic_type"}, dataProvider = "type")
    public void getTokenAnalyticTypeWithTokenIdInvalid(String type){
        ArrayList<String> tokenIdInvalid = new ArrayList<>();
        tokenIdInvalid.add("@#$");
        tokenIdInvalid.add("  ");
        tokenIdInvalid.add("12345");
        tokenIdInvalid.add("abcd");
        for(String a : tokenIdInvalid){
            tokenSteps.getTokenAnalyticsType(a, type)
                    .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, TokenConstants.ERROR_MESSAGE, TokenConstants.ERROR_CODE);
        }
    }
//    @Test(description = "get token analytics type | type invalid", groups = {"token", "token_analytic_type"}, dataProvider = "typeInvalid")
//    public void getTokenAnalyticTypeWithTypeInvalid(String typeInvalid){
//        tokenSteps.getTokenAnalyticsType(tokenId, typeInvalid)
//                .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, TokenConstants.ERROR_CODE_ANALYTIC, TokenConstants.ERROR_MESSAGE_ANLYTIC);
//    }
//    @DataProvider(name = "typeInvalid")
//    public Object[][] DataSetTypeInvalid(){
//        return new Object[][]{
//                {"TWO_DAY"},
//                {"THREE_WEEK"},
//                {"TWO_MONTH"},
//                {"ONEDAY"},
//                {"ONE DAY"},
//                {"one_day"},
//                {"1_day"},
//                {"5/04/2023"},
//                {" "}
//        };
//    }
}
