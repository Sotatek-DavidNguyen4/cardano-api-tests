package tests.tokens;

import base.BaseTest;
import microservices.token.models.TokensTopHolderModel;
import microservices.token.steps.TokenSteps;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;

public class TokenTopHolder extends BaseTest {
    private TokenSteps tokenSteps = new TokenSteps();
    private String tokenId = "asset1546zv6l65l26nwnf2vs4s4nyyr5x65uhpu9hud";
    @Test(description = "get token top holder", groups = {"token", "top_holder"})
    public void getTokenTopHolder(){
        TokensTopHolderModel tokensTopHolderModel = (TokensTopHolderModel)
        tokenSteps.getTokenTopHolder(tokenId)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TokensTopHolderModel.class);
    }
}
