package tests;

import base.BaseTest;
import microservices.steps.protocol.TokenSteps;
import org.testng.annotations.Test;

public class tokenTests extends BaseTest {
    TokenSteps tokenSteps = new TokenSteps();
    @Test
    public void getListToken(){
        System.out.println(tokenSteps.getTokens());
    }

}
