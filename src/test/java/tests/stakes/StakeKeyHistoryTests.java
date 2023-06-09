package tests.stakes;

import base.BaseTest;
import microservices.stakeKey.constants.StakeKeyConstants;
import microservices.stakeKey.models.DataHistory;
import microservices.stakeKey.models.StakeHistory;
import microservices.stakeKey.steps.StakeKeySteps;
import org.apache.commons.collections.MultiMap;
import org.checkerframework.checker.units.qual.A;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateMultiParameters;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Map;

public class StakeKeyHistoryTests extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    private String stake = "stake1u9pwlay8fvev5yfvnpx8c057n3m0aa5g493qw44zqdycz6sm0cph9";
    private long numberPage;
    @Test(description = "get stake history", groups = {"stake", "stake_history"}, priority = 0)
    public void getStakeHistory(){
        String pathStakeHistorySchema = "schemaJson/stakes/stakeHistory.json";
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        StakeHistory stakeHistory = (StakeHistory) stakeKeySteps
                .getStakeHistory(stake)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(StakeHistory.class);

        numberPage = stakeHistory.getTotalItems() + 1 ;
        ArrayList<Object> elements = new ArrayList<>();
        for(DataHistory dataHistory : stakeHistory.getData()){
            elements.add(dataHistory.getFee());
        }

        stakeKeySteps.then_verifyPageStakeHistoryResponse(stakeHistory, param)
                .then_verifySizeStakeHistoryResponse(stakeHistory, param, 1)
                .verifyStakeHistory(stakeHistory.getData())
                .verifyElementsIsNotDecimal(elements)
                .validateResponseSchema(pathStakeHistorySchema);
    }
    @Test(description = "get stake history with invalid page", groups = {"stake", "stake_history"}, dataProvider = "page")
    public void getStakeHistoryInvalidPage(Object page){
        MultiMap params = new CreateMultiParameters()
                .withPage(page)
                .getParamsMap();
        StakeHistory stakeHistory = (StakeHistory) stakeKeySteps
                .getStakeHistoryAllKey(stake, params)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(StakeHistory.class);
        stakeKeySteps.then_verifyPageStakeHistoryResponse(stakeHistory, params);
    }
    @DataProvider(name = "page")
    public Object[][] DatasetPage(){
        return new Object[][]{
//                {"1"},
                {"abc"},
                {"-10"},
                {"  "},
                {"@#$"},
        };
    }
    @Test(description = "get stake history with size", groups = {"stake", "stake_history"}, dataProvider = "size")
    public void getStakeHistoryWithSize(Object size){
        MultiMap params = new CreateMultiParameters()
                .withPageSize(size)
                .getParamsMap();
        StakeHistory stakeHistory = (StakeHistory) stakeKeySteps
                .getStakeHistoryAllKey(stake, params)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(StakeHistory.class);
        stakeKeySteps.then_verifySizeStakeHistoryResponse(stakeHistory, params, 1);
    }
    @DataProvider(name = "size")
    public Object[][] DatasetSize(){
        return new Object[][]{
                {"1"},
                {"abc"},
                {"-10"},
                {"  "},
                {"@#$"},
        };
    }
}
