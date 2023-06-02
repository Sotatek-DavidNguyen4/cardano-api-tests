package tests.stakes;

import base.BaseTest;
import microservices.stakeKey.constants.StakeKeyConstants;
import microservices.stakeKey.models.StakeHistory;
import microservices.stakeKey.steps.StakeKeySteps;
import org.apache.commons.collections.MultiMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateMultiParameters;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

public class StakeKeyHistoryTests extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    private String stake = "stake_test1uq7zcz97zpefr05dwxamxtdpruaewcdsny0j5mmfgr608yqwmes43";
    @Test(description = "get stake history", groups = {"stake", "stake_history"})
    public void getStakeHistory(){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        StakeHistory stakeHistory = (StakeHistory) stakeKeySteps
                .getStakeHistory(stake)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(StakeHistory.class);
        stakeKeySteps.then_verifyFilterStakeHistoryResponse(stakeHistory, param)
                .verifyStakeHistory(stakeHistory.getData());
    }
    @Test(description = "get stake history all key", groups = {"stake", "stake_history"})
    public void getStakeHistoryAllKey(){
        MultiMap param = new CreateMultiParameters()
                .withPage("0")
                .withPageSize("20")
                .getParamsMap();
        StakeHistory stakeHistory = (StakeHistory) stakeKeySteps
                .getStakeHistoryAllKey(stake, param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(StakeHistory.class);
        stakeKeySteps.then_verifyFilterStakeHistoryResponse(stakeHistory, param);
    }
    @Test(description = "get stake history with invalid stake key", groups = {"stake", "stake_history"}, dataProvider = "stakeKeyInvalid")
    public void getStakeHistoryInvalidStake(Object stakeKey){
        stakeKeySteps
                .getStakeHistory(stakeKey)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, StakeKeyConstants.ERROR_MESSAGE,StakeKeyConstants.ERROR_CODE);
    }
    @DataProvider(name = "stakeKeyInvalid")
    public Object[][] DatasetStakeInvalid(){
        return new Object[][]{
                {"@#$"},
                {" "},
                {"abcd"},
                {"12345"}
        };
    }
    @Test(description = "get stake history with invalid page", groups = {"stake", "stake_history"}, dataProvider = "invalidParams")
    public void getStakeHistoryInvalidPage(Object page, Object size){
        MultiMap params = new CreateMultiParameters()
                .withPage(page)
                .withPageSize(size)
                .getParamsMap();
        StakeHistory stakeHistory = (StakeHistory) stakeKeySteps
                .getStakeHistoryAllKey(stake, params)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(StakeHistory.class);
        stakeKeySteps.then_verifyFilterStakeHistoryResponse(stakeHistory, params);
    }
    @DataProvider(name = "invalidParams")
    public Object[][] DatasetParamInvalid(){
        return new Object[][]{
//                {1, null},
                {"abc", null},
                {"-10", null},
                {"  ", null},
                {"@#$%", null},

                {null, "10"},
                {null, "abc"},
                {null, "-10"},
                {null, "  "},
                {null, "@#$%"}
        };
    }
}
