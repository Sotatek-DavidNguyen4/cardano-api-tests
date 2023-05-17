package tests.epoch;

import base.BaseTest;
import microservices.epoch.models.EpochCurrent;
import microservices.epoch.steps.EpochSteps;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;

public class EpochCurrentTests extends BaseTest {
    private EpochSteps epochSteps = new EpochSteps();
    private EpochCurrent epochCurrent = new EpochCurrent();

    @Test(description = "Verify get current epoch successfully" ,groups = {"epoch"})
    public void getCurrentEpoch(){
        epochSteps.getCurrentEpoch()
                  .validateResponse(HttpURLConnection.HTTP_OK);
        epochCurrent = epochSteps.saveResponseCurrentEpoch();

        epochSteps.verifyEpochCurrentResponseNotNull(epochCurrent)
                  .verifyEpochCurrentResponse(epochCurrent,432000);
    }
}
