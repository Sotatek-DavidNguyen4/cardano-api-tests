package tests.stakes;

import base.BaseTest;
import microservices.stakeKey.models.history.DelegationHistoryModel;
import microservices.stakeKey.steps.StakeKeySteps;
import org.apache.commons.collections.MultiMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateMultiParameters;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

public class StakeKeyDelegationHistoryTest extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    private String stakeKey = "stake_test17rs4wd04fx4ls9rpdelrp9qwnh3w6cexlmgj42h5t0tvv8gjrqqjc";
    @Test(description = "get stake delegation history", groups = {"stake", "stake_delegation_history"})
    public void getStakeDelegationHistory(){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        DelegationHistoryModel delegationHistoryModel = (DelegationHistoryModel)
                stakeKeySteps.getDelegationHistory(stakeKey, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(DelegationHistoryModel.class);
        stakeKeySteps.then_verifyFilterStakeDelegationHistoryResponse(delegationHistoryModel, param, 20);
    }
    @Test(description = "get stake delegation history | all key", groups = {"stake", "stake_delegation_history"})
    public void getStakeDelegationHistoryAllKey(){
        MultiMap param = new CreateMultiParameters()
                .withPage("0")
                .withPageSize("20")
                .getParamsMap();
        DelegationHistoryModel delegationHistoryModel = (DelegationHistoryModel)
                stakeKeySteps.getDelegationHistory(stakeKey, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(DelegationHistoryModel.class);
        stakeKeySteps.then_verifyFilterStakeDelegationHistoryResponse(delegationHistoryModel, param, 20);
    }
    @Test(description = "get stake delegation history | stakeKey invalid", groups = {"stake", "stake_delegation_history"}, dataProvider = "stakeKey")
    public void getDelegationHistoryStakeKeyInvalid(String stakeKey){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        DelegationHistoryModel delegationHistoryModel = (DelegationHistoryModel)
                stakeKeySteps.getDelegationHistory(stakeKey, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(DelegationHistoryModel.class);
        stakeKeySteps.then_verifyFilterStakeDelegationHistoryResponse(delegationHistoryModel, param, 0);
    }
    @DataProvider(name = "stakeKey")
    public Object[][] DatasetStakeKeyInvalid() {
        return new Object[][]{
                {"@#$"},
                {" "},
                {"abc"},
                {"12345"}
        };
    }
    @Test(description = "get stake delegation history with page", groups = {"stake", "stake_delegation_history"}, dataProvider = "page")
    public void getDelegationHistoryWithPage(String page){
        MultiMap param = new CreateMultiParameters()
                .withPage(page)
                .getParamsMap();
        DelegationHistoryModel delegationHistoryModel = (DelegationHistoryModel)
                stakeKeySteps.getDelegationHistory(stakeKey, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(DelegationHistoryModel.class);
        stakeKeySteps.then_verifyFilterStakeDelegationHistoryResponse(delegationHistoryModel, param, 20);
    }
    @DataProvider (name = "page")
    public Object[][] DatasetPage() {
        return new Object[][]{
//                {"9"},
                {"abc"},
                {"-10"},
                {"  "},
                {"@#$%%"}
        };
    }
    @Test(description = "get stake delegation history with size", groups = {"stake", "stake_delegation_history"}, dataProvider = "size")
    public void getDelegationHistoryWithSize(String size){
        MultiMap param = new CreateMultiParameters()
                .withPageSize(size)
                .getParamsMap();
        DelegationHistoryModel delegationHistoryModel = (DelegationHistoryModel)
                stakeKeySteps.getDelegationHistory(stakeKey, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(DelegationHistoryModel.class);
        stakeKeySteps.then_verifyFilterStakeDelegationHistoryResponse(delegationHistoryModel, param, 20);
    }
    @DataProvider (name = "size")
    public Object[][] DatasetSize() {
        return new Object[][]{
                {"9"},
                {"abc"},
                {"-10"},
                {"  "},
                {"@#$%%"}
        };
    }
}
