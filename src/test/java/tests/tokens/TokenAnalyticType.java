package tests.tokens;

import base.BaseTest;
import microservices.token.models.TokenAnalytics;
import microservices.token.steps.TokenSteps;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.util.List;

public class TokenAnalyticType extends BaseTest {
    private TokenSteps tokenSteps = new TokenSteps();
    private String tokenId = "asset17q7r59zlc3dgw0venc80pdv566q6yguw03f0d9";
    private String type = "ONE_DAY";
    @Test(description = "get token analytics type", groups = {"token", "token_analytic_type"})
    public void getTokenAnalyticType(){
        List<TokenAnalytics> ananlytics = tokenSteps.getTokenAnalyticsType(tokenId, type)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseListObject(TokenAnalytics[].class);
        tokenSteps.verifyResponseTokenAnalyticsType(ananlytics);
    }
}
