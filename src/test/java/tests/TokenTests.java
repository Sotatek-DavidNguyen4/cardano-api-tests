package tests;

import base.BaseTest;
import microservices.steps.token.service.TokenSteps;
import org.testng.annotations.Test;

public class TokenTests extends BaseTest {
    @Test(description = "verify that get token successfull", groups={"token"})
    public void getListToken(){
        TokenSteps tokenSteps = new TokenSteps();
        tokenSteps.getListTokens();
    }
}
