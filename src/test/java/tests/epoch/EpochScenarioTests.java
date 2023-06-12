package tests.epoch;

import base.BaseTest;
import microservices.epoch.models.epoch.Epoch;
import microservices.epoch.models.epoch.EpochData;
import microservices.epoch.steps.EpochSteps;
import org.testng.annotations.Test;
import util.RamdomNumber;

import java.net.HttpURLConnection;


public class EpochScenarioTests extends BaseTest {
    private EpochSteps epochSteps = new EpochSteps();
    private EpochData epochData = new EpochData();
    private RamdomNumber ramdomNumber = new RamdomNumber();

    @Test(description = "take epoch detail", groups = {"epoch", "epoch_scenario"})
    public void takeEpochDetail(){
        Epoch epoch = (Epoch)
        epochSteps.getListEpoch()
            .validateStatusCode(HttpURLConnection.HTTP_OK)
            .saveResponseObject(Epoch.class);
        epochData = epoch.getData().get(ramdomNumber.ramdomInger(epoch.getData().size()));
    }

}
