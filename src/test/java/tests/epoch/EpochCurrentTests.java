package tests.epoch;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import microservices.epoch.models.EpochCurrent;
import microservices.epoch.steps.EpochSteps;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;

@Epic("cardano")
@Feature("api-epoch")
@Story("GET: Get current epoch")
public class EpochCurrentTests extends BaseTest {
    private EpochSteps epochSteps = new EpochSteps();
    private EpochCurrent epochCurrent = new EpochCurrent();

    @Test(description = "Verify get current epoch successfully" ,groups = {"epoch"})
    public void getCurrentEpoch(){
        String currentEpochSchema = "schemaJson/epoch/currentEpoch.json";
        epochCurrent = (EpochCurrent) epochSteps.getCurrentEpoch()
                                                .validateResponse(HttpURLConnection.HTTP_OK)
                                                .validateResponseSchema(currentEpochSchema)
                                                .saveResponseObject(EpochCurrent.class);

        epochSteps.verifyEpochCurrentResponseNotNull(epochCurrent)
                  .verifyEpochCurrentResponse(epochCurrent,432000);
    }
}
