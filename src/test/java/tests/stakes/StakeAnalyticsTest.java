package tests.stakes;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import microservices.stakeKey.models.Analytics.StakeAnalytics;
import microservices.stakeKey.models.deRegistration.StakeDeRegistration;
import microservices.stakeKey.models.deRegistration.StakeDeRegistrationData;
import microservices.stakeKey.steps.StakeKeySteps;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.util.List;


@Epic("cardano")
@Feature("api-stake-key-controller")
@Story("Get active stake, live stake and total stake")
public class StakeAnalyticsTest extends BaseTest {
    StakeKeySteps stakeKeySteps = new StakeKeySteps();
    StakeAnalytics stakeAnalytics ;
    @Test(description = "Verify active stake, live stake and total stake",groups = "stake-key-controller")
    public void getDataForStakeDeRegistration(){
        stakeAnalytics = (StakeAnalytics) stakeKeySteps.getStakeAnalytics()
                                                .validateResponse(HttpURLConnection.HTTP_OK)
                                                .saveResponseObject(StakeAnalytics.class);

        stakeKeySteps.then_verifyStakeAnalyticsResponseNotNull(stakeAnalytics);

    }
}
