package tests.stakes;

import base.BaseTest;
import microservices.stakeKey.models.withdrawalHistory.WithdrawalHistoryModel;
import microservices.stakeKey.steps.StakeKeySteps;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

public class StakeKeyWithdrawalHistoryTests extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    private String stakeKey = "stake_test1uq7zcz97zpefr05dwxamxtdpruaewcdsny0j5mmfgr608yqwmes43";
    @Test(description = "get stake withdrawal history", groups = {"stake", "stake_withdrawal_history"})
    public void getStakeKeyWithdrawalHistory(){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        WithdrawalHistoryModel withdrawalHistoryModel = (WithdrawalHistoryModel)
        stakeKeySteps.getStakeKeyWithdrawalHistory(stakeKey, param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(WithdrawalHistoryModel.class);
        stakeKeySteps.then_verifyFilterStakeWithdrawalHistoryResponse(withdrawalHistoryModel, param, 0);
    }
}
