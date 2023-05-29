package tests.stakes;

import base.BaseTest;
import microservices.stakeKey.constants.StakeKeyConstants;
import microservices.stakeKey.models.StakeModel;
import microservices.stakeKey.steps.StakeKeySteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class Stake extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    private String stakeKey = "stake_test1urxpn7ymps94ech32hx7xm667kg3gdv52fgzfwqxaprp2zck8kezr";
    private String poolId = "pool132jxjzyw4awr3s75ltcdx5tv5ecv6m042306l630wqjckhfm32r";

    @Test(description = "get stake with stake key", groups = {"stake", "stake_key"})
    public void getStake(){
        Map<String, Object> expected = new HashMap<>();
        expected.put("stakeAddress", stakeKey);
        expected.put("poolId", poolId);
        StakeModel stakeModel = (StakeModel)
        stakeKeySteps.getStakeWithStakeKey(stakeKey)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(StakeModel.class);
        stakeKeySteps.verifyResponseStake(stakeModel, expected);
    }
    @Test(description = "get stake with stake key | unsuccess", groups = {"stake", "stake_key"}, dataProvider = "stakeKey")
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
