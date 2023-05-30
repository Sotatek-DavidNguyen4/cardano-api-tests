package tests.stakes;

import base.BaseTest;
import microservices.stakeKey.models.withdrawalHistory.WithdrawalHistoryModel;
import microservices.stakeKey.steps.StakeKeySteps;
import org.apache.commons.collections.MultiMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateMultiParameters;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

public class StakeKeyWithdrawalHistoryTests extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    private String stakeKeyNoWithdrawal = "stake_test1uq7zcz97zpefr05dwxamxtdpruaewcdsny0j5mmfgr608yqwmes43";
    private String stakeKeyWithdrawal = "stake_test17qdcxaxsk222fgeenle97p4qwpa3vg20r6dk0nufhgj9ffsytg3a0";
    @Test(description = "get stake withdrawal history | hasn't withdrawal", groups = {"stake", "stake_withdrawal_history"})
    public void getStakeKeyWithdrawalHistoryNoWithdrawal(){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        WithdrawalHistoryModel withdrawalHistoryModel = (WithdrawalHistoryModel)
        stakeKeySteps.getStakeKeyWithdrawalHistory(stakeKeyNoWithdrawal, param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(WithdrawalHistoryModel.class);
        stakeKeySteps.then_verifyFilterStakeWithdrawalHistoryResponse(withdrawalHistoryModel, param, 0);
    }
    @Test(description = "get stake withdrawal history | withdrawal", groups = {"stake", "stake_withdrawal_history"})
    public void getStakeKeyWithdrawalHistory(){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        WithdrawalHistoryModel withdrawalHistoryModel = (WithdrawalHistoryModel)
                stakeKeySteps.getStakeKeyWithdrawalHistory(stakeKeyWithdrawal, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(WithdrawalHistoryModel.class);
        stakeKeySteps.then_verifyFilterStakeWithdrawalHistoryResponse(withdrawalHistoryModel, param, 20);
    }
    @Test(description = "get stake withdrawal history | stakeKey invalid", groups = {"stake", "stake_withdrawal_history"}, dataProvider = "stakeKey")
    public void getWithdrawalHistoryStakeKeyInvalid(String stakeKey){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        WithdrawalHistoryModel withdrawalHistoryModel = (WithdrawalHistoryModel)
                stakeKeySteps.getStakeKeyWithdrawalHistory(stakeKey, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(WithdrawalHistoryModel.class);
        stakeKeySteps.then_verifyFilterStakeWithdrawalHistoryResponse(withdrawalHistoryModel, param, 0);
    }
    @DataProvider (name = "stakeKey")
    public Object[][] DatasetStakeKeyInvalid() {
        return new Object[][]{
                {"@#$"},
                {" "},
                {"abc"},
                {"12345"}
        };
    }
    @Test(description = "get stake withdrawal history with page", groups = {"stake", "stake_withdrawal_history"}, dataProvider = "page")
    public void getWithdrawalHistoryWithPage(String page){
        MultiMap param = new CreateMultiParameters()
                .withPage(page)
                .getParamsMap();
        WithdrawalHistoryModel withdrawalHistoryModel = (WithdrawalHistoryModel)
                stakeKeySteps.getStakeKeyWithdrawalHistory(stakeKeyWithdrawal, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(WithdrawalHistoryModel.class);
        stakeKeySteps.then_verifyFilterStakeWithdrawalHistoryResponse(withdrawalHistoryModel, param, 20);
    }
    @DataProvider (name = "page")
    public Object[][] DatasetPage() {
        return new Object[][]{
                {"10"},
                {"abc"},
                {"-10"},
                {"  "},
                {"@#$%%"}
        };
    }
    @Test(description = "get stake withdrawal history with size", groups = {"stake", "stake_withdrawal_history"}, dataProvider = "size")
    public void getWithdrawalHistoryWithSize(String size){
        MultiMap param = new CreateMultiParameters()
                .withPageSize(size)
                .getParamsMap();
        WithdrawalHistoryModel withdrawalHistoryModel = (WithdrawalHistoryModel)
                stakeKeySteps.getStakeKeyWithdrawalHistory(stakeKeyWithdrawal, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(WithdrawalHistoryModel.class);
        stakeKeySteps.then_verifyFilterStakeWithdrawalHistoryResponse(withdrawalHistoryModel, param, 20);
    }
    @DataProvider (name = "size")
    public Object[][] DatasetSize() {
        return new Object[][]{
                {"10"},
                {"v"},
                {"-10"},
                {"  "},
                {" !@@$$"}
        };
    }
}
