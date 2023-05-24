package tests.stakes;

import base.BaseTest;
import microservices.stakeKey.steps.StakeKeySteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

public class StakeKeyInstantaneousRewards extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    private String stakeKey = "stake_test1urz3dwcwdhvl9dy75rj5krvpmjyldeyxqx5p6xms6xyjd7quqmq2p";

    @Test(description = "get stake instantaneous rewards with stake key", groups = {"stakeKey"}, dataProvider = "stakeKeyInstantaneousReward")
    public void getStakeInstantaneousReward(Object stakeKey){
        stakeKeySteps.getStakeInstantaneousRewards(stakeKey)
                .validateStatusCode(HttpURLConnection.HTTP_OK);
    }
    @DataProvider(name = "stakeKeyInstantaneousReward")
    public Object[][] DatasetStakeKeyInstantaneousRewards(){
        return new Object[][]{
                {stakeKey},
//                {"@#$%"},
                {"  "},
                {"abcd"},
                {"12345"}
        };
    }
    @Test(description = "get stake instantaneous rewards with param", groups = "stakeKey", dataProvider = "paramInstantaneousReward")
    public void getStakkeInstantaneousRewardParam(Object page, Object size){
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .getParamsMap();
        stakeKeySteps.getStakeInstantaneousRewardParam(stakeKey, param)
                .validateStatusCode(HttpURLConnection.HTTP_OK);
    }
    @DataProvider(name = "paramInstantaneousReward")
    public Object[][] DatasetParamStakeInstantaneousReward(){
        return new Object[][]{
//                {9, null},
                {"abc", null},
                {-10, null},
                {" ", null},
                {"@#$%", null},

                {null, 1},
                {null, "abc"},
                {null, -10},
                {null, " "},
                {null, "@#$%"}
        };
    }
}
