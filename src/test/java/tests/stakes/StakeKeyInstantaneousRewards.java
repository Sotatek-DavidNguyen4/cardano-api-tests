package tests.stakes;

import base.BaseTest;
import microservices.stakeKey.models.StakeHistory;
import microservices.stakeKey.steps.StakeKeySteps;
import org.apache.commons.collections.MultiMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateMultiParameters;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

public class StakeKeyInstantaneousRewards extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    private String stakeKey = "stake_test1urz3dwcwdhvl9dy75rj5krvpmjyldeyxqx5p6xms6xyjd7quqmq2p";
    @Test(description = "get stake instantaneous rewards with stake key", groups = {"stake", "stake_instantaneour_rewards"}, dataProvider = "stakeKeyInstantaneousReward")
    public void getStakeInstantaneousReward(Object stakeKey){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        StakeHistory stakeHistory = (StakeHistory)
        stakeKeySteps.getStakeInstantaneousRewards(stakeKey)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(StakeHistory.class);
        stakeKeySteps.then_verifyFilterStakeHistoryResponse(stakeHistory, param);
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
    @Test(description = "get stake instantaneous rewards with param", groups = {"stake", "stake_instantaneour_rewards"}, dataProvider = "paramInstantaneousReward")
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
