package tests.stakes;

import base.BaseTest;
import microservices.stakeKey.constants.StakeKeyConstants;
import microservices.stakeKey.steps.StakeKeySteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;

public class Stake extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    @Test(description = "get stake with stake key", groups = {"stakeKey"})
    public void getStake(){
        String stakeKey = "stake_test1urxpn7ymps94ech32hx7xm667kg3gdv52fgzfwqxaprp2zck8kezr";
        stakeKeySteps.getStakeWithStakeKey(stakeKey)
                .validateStatusCode(HttpURLConnection.HTTP_OK);
    }
    @Test(description = "get stake with stake key | unsuccess", groups = {"stakeKey"}, dataProvider = "stakeKey")
    public void getStakeUnsuccess(Object stakeKey){
        stakeKeySteps.getStakeWithStakeKey(stakeKey)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, StakeKeyConstants.ERROR_MESSAGE, StakeKeyConstants.ERROR_CODE);
    }
    @DataProvider(name = "stakeKey")
    public Object[][] DatasetStakeKey(){
        return new Object[][]{
                {"stake_test1urf6y7ktcqwzxd2tc3x54437jl6vcqvazgrdka3v2njdjzgyn6hgy"},
//                {"@#$%"},
                {"  "},
                {"abcd"},
                {"12345"}
        };
    }
}
