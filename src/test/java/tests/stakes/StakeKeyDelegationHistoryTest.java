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
    private String stakeKey = "stake1u8u9cfsxwvkvhydyku82d0vect72vfaenrqrtz8xlfdez6sjw7taf";
    private int numberPage;
    @Test(description = "get stake delegation history", groups = {"stake", "stake_delegation_history"}, priority = 0)
    public void getStakeDelegationHistory(){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        DelegationHistoryModel delegationHistoryModel = (DelegationHistoryModel)
                stakeKeySteps.getDelegationHistory(stakeKey, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(DelegationHistoryModel.class);
        numberPage = delegationHistoryModel.getTotalItems()/5 + 1;
        stakeKeySteps.then_verifySizeDelegationHistoryResponse(delegationHistoryModel, param, 5)
                .then_verifyCurrentPageDelegationHistoryResponse(delegationHistoryModel, param)
                .verifyStakeDelegationHistory(delegationHistoryModel.getData());
    }
    @Test(description = "get stake delegation history | stakeKey invalid", groups = {"stake", "stake_delegation_history"}, dataProvider = "stakeKey")
    public void getDelegationHistoryStakeKeyInvalid(String stakeKey){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        DelegationHistoryModel delegationHistoryModel = (DelegationHistoryModel)
                stakeKeySteps.getDelegationHistory(stakeKey, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(DelegationHistoryModel.class);
        stakeKeySteps.then_verifySizeDelegationHistoryResponse(delegationHistoryModel, param, 0)
                .then_verifyCurrentPageDelegationHistoryResponse(delegationHistoryModel, param);
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
        stakeKeySteps.then_verifyCurrentPageDelegationHistoryResponse(delegationHistoryModel, param)
                .then_verifySizeDelegationHistoryResponse(delegationHistoryModel, param, 5);
    }
    @DataProvider (name = "page")
    public Object[][] DatasetPage() {
        return new Object[][]{
//                {"10"},
                {"abc"},
                {"-10"},
                {"  "},
                {"@#$"}
        };
    }
    @Test(description = "get stake delegation history with page = totalPage + 1", groups = {"stake", "stake_delegation_history"}, priority = 1)
    public void getDelegationHistoryWithPage2(){
        MultiMap param = new CreateMultiParameters()
                .withPage(""+numberPage+"")
                .getParamsMap();
        DelegationHistoryModel delegationHistoryModel = (DelegationHistoryModel)
                stakeKeySteps.getDelegationHistory(stakeKey, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(DelegationHistoryModel.class);
        stakeKeySteps.then_verifyCurrentPageDelegationHistoryResponse(delegationHistoryModel, param)
                .then_verifySizeDelegationHistoryResponse(delegationHistoryModel, param, 0);
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
        stakeKeySteps.then_verifySizeDelegationHistoryResponse(delegationHistoryModel, param, 5);
    }
    @DataProvider (name = "size")
    public Object[][] DatasetSize() {
        return new Object[][]{
//                {"10"},
                {"abc"},
                {"-10"},
                {"  "},
                {"!@@$$"}
        };
    }
}
