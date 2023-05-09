package tests;

import base.BaseTest;
import microservices.steps.token.step.TokenSteps;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TokenTests extends BaseTest {
    @Test(description = "verify that get list token successfull with page = 20, size = 2 and sort asc", groups={"token"})
    public void getListTokenDesc(){
        ArrayList<String> sort = new ArrayList<>();
        sort.add("supply,DESC");
        TokenSteps tokenSteps = new TokenSteps();
        System.out.println(tokenSteps.getListTokens(20, 2, sort));;
    }
    @Test(description = "verify that get list token successfull with page = 20, size = 2 and sort asc", groups={"token"})
    public void getListTokenAsc(){
        ArrayList<String> sort = new ArrayList<>();
        sort.add("supply,ASC");
        TokenSteps tokenSteps = new TokenSteps();
        System.out.println(tokenSteps.getListTokens(20, 2, sort));;
    }
}
