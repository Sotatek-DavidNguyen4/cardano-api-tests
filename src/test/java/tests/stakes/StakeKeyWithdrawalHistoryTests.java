package tests.stakes;

import base.BaseTest;
import microservices.stakeKey.models.history.WithdrawalHistoryDataModel;
import microservices.stakeKey.models.history.WithdrawalHistoryModel;
import microservices.stakeKey.steps.StakeKeySteps;
import org.apache.commons.collections.MultiMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateMultiParameters;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Map;

public class StakeKeyWithdrawalHistoryTests extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    private String stakeKeyNoWithdrawal = "stake1u9pwlay8fvev5yfvnpx8c057n3m0aa5g493qw44zqdycz6sm0cph9Author";
    private String stakeKeyWithdrawal = "stake1u8v6dwu5tumz850n3uq2sepd9hxz7tjupnjnn5cktzats6qs3u0kv";
    private int numberPage;
    @Test(description = "get stake withdrawal history | hasn't withdrawal", groups = {"stake", "stake_withdrawal_history"})
    public void getStakeKeyWithdrawalHistoryNoWithdrawal(){
        String pathStakeWithdrawalHistorySchema = "schemaJson/stakes/stakeWithdrawalHistory.json";
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        WithdrawalHistoryModel withdrawalHistoryModel = (WithdrawalHistoryModel)
        stakeKeySteps.getStakeKeyWithdrawalHistory(stakeKeyNoWithdrawal, param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(WithdrawalHistoryModel.class);
        stakeKeySteps.then_verifyPageStakeWithdrawalHistoryResponse(withdrawalHistoryModel, param)
                .then_verifySizeStakeWithdrawalHistoryResponse(withdrawalHistoryModel, param, 0)
                .validateResponseSchema(pathStakeWithdrawalHistorySchema);
    }
    @Test(description = "get stake withdrawal history | withdrawal", groups = {"stake", "stake_withdrawal_history"}, priority = 0)
    public void getStakeKeyWithdrawalHistory(){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        WithdrawalHistoryModel withdrawalHistoryModel = (WithdrawalHistoryModel)
                stakeKeySteps.getStakeKeyWithdrawalHistory(stakeKeyWithdrawal, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(WithdrawalHistoryModel.class);

        ArrayList<Object> elements = new ArrayList<>();
        for (WithdrawalHistoryDataModel withdrawalHistoryDataModel : withdrawalHistoryModel.getData()){
            elements.add(withdrawalHistoryDataModel.getFee());
            elements.add(withdrawalHistoryDataModel.getAmount());
        }
        numberPage = withdrawalHistoryModel.getTotalItems()/20 + 1;

        stakeKeySteps.then_verifyPageStakeWithdrawalHistoryResponse(withdrawalHistoryModel, param)
                .then_verifySizeStakeWithdrawalHistoryResponse(withdrawalHistoryModel, param, 20)
                .verifyStakeWithdrawalHistory(withdrawalHistoryModel.getData())
                .verifyElementsIsNotDecimal(elements);
    }
    @Test(description = "get stake withdrawal history | stakeKey invalid", groups = {"stake", "stake_withdrawal_history"}, dataProvider = "stakeKey")
    public void getWithdrawalHistoryStakeKeyInvalid(String stakeKey){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        WithdrawalHistoryModel withdrawalHistoryModel = (WithdrawalHistoryModel)
                stakeKeySteps.getStakeKeyWithdrawalHistory(stakeKey, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(WithdrawalHistoryModel.class);
        stakeKeySteps.then_verifyPageStakeWithdrawalHistoryResponse(withdrawalHistoryModel, param)
                .then_verifySizeStakeWithdrawalHistoryResponse(withdrawalHistoryModel, param, 0);
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
        stakeKeySteps.then_verifyPageStakeWithdrawalHistoryResponse(withdrawalHistoryModel, param);
    }
    @DataProvider (name = "page")
    public Object[][] DatasetPage() {
        return new Object[][]{
                {"1"},
                {"abc"},
                {"-10"},
                {"  "},
                {"@#$"}
        };
    }
    @Test(description = "get stake withdrawal history with page = totalPage + 1", groups = {"stake", "stake_withdrawal_history"}, priority = 1)
    public void getWithdrawalHistoryWithPage2(){
        MultiMap param = new CreateMultiParameters()
                .withPage(""+numberPage+"")
                .getParamsMap();
        WithdrawalHistoryModel withdrawalHistoryModel = (WithdrawalHistoryModel)
                stakeKeySteps.getStakeKeyWithdrawalHistory(stakeKeyWithdrawal, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(WithdrawalHistoryModel.class);
        stakeKeySteps.then_verifyPageStakeWithdrawalHistoryResponse(withdrawalHistoryModel, param)
                .then_verifySizeStakeWithdrawalHistoryResponse(withdrawalHistoryModel, param, 0);
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
        stakeKeySteps.then_verifySizeStakeWithdrawalHistoryResponse(withdrawalHistoryModel, param, 20);
    }
    @DataProvider (name = "size")
    public Object[][] DatasetSize() {
        return new Object[][]{
                {"10"},
                {"abc"},
                {"-10"},
                {"  "},
                {"!@@$$"}
        };
    }
}
