package tests.stakes;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import microservices.stakeKey.models.StakeHistory;
import microservices.stakeKey.models.StakeModel;
import microservices.stakeKey.models.topDelegators.TopDelegators;
import microservices.stakeKey.steps.StakeKeySteps;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;

import static constants.AttributeFormats.STATKE_ADDRESS_LENGTH;
import static constants.Environment.isPreProd;


@Epic("cardano")
@Feature("api-stake-key-controller")
@Story("StakeKey Scenario")
public class StakeKeyScenarioTest extends BaseTest {
    StakeKeySteps stakeKeySteps = new StakeKeySteps();
    TopDelegators topDelegators ;

    @Test(description = "Compare api: Get top delegators with api: Get a stake detail by stake key",groups = {"stake","stake_scenario"})
    public void getDataForStakeDelegatorAndDetail(){
        MultiMap params = new MultiValueMap();
        params.put("page", null);
        params.put("size", null);
        topDelegators = (TopDelegators) stakeKeySteps.getTopDelegators(params)
                                                .validateResponse(HttpURLConnection.HTTP_OK)
                                                .saveResponseObject(TopDelegators.class);

        StakeModel stakeModel = (StakeModel) stakeKeySteps.getStakeWithStakeKey(topDelegators.getData().get(0).getStakeKey())
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(StakeModel.class);

        stakeKeySteps.then_verifyStakeResponseTopAndDetail(topDelegators,stakeModel);
    }

    @Test(description = "Compare api: Get a stake detail by stake keywith api: Get delegation history of stake key",groups = {"stake","stake_scenario"})
    public void getDataForStakeDelegatorAndDelegationHistory(){
        MultiMap params = new MultiValueMap();
        params.put("page", null);
        params.put("size", null);
        topDelegators = (TopDelegators) stakeKeySteps.getTopDelegators(params)
                                                .validateResponse(HttpURLConnection.HTTP_OK)
                                                .saveResponseObject(TopDelegators.class);

        StakeHistory stakeHistory = (StakeHistory) stakeKeySteps.getStakeHistory(topDelegators.getData().get(0).getStakeKey())
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(StakeHistory.class);

//        stakeKeySteps.then_verifyStakeResponseTopAndDetail(topDelegators,stakeModel);
    }
}
