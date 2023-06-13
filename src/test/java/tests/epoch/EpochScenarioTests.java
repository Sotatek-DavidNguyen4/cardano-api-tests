package tests.epoch;

import base.BaseTest;
import microservices.epoch.models.EpochCurrent;
import microservices.epoch.models.epoch.Epoch;
import microservices.epoch.models.epoch.EpochData;
import microservices.epoch.models.epochByEpochNo.EpochByEpochNo;
import microservices.epoch.steps.EpochSteps;
import org.testng.annotations.Test;
import util.RamdomNumber;

import java.net.HttpURLConnection;


public class EpochScenarioTests extends BaseTest {
    private EpochSteps epochSteps = new EpochSteps();
    private EpochData epochData = new EpochData();
    private Epoch listEpoch = new Epoch();
    private RamdomNumber ramdomNumber = new RamdomNumber();

    @Test(description = "take epoch detail", groups = {"epoch", "epoch_scenario"}, priority = 0)
    public void takeEpochDetail(){
        Epoch epoch = (Epoch)
        epochSteps.getListEpoch()
            .validateStatusCode(HttpURLConnection.HTTP_OK)
            .saveResponseObject(Epoch.class);
        listEpoch = epoch;
        epochData = epoch.getData().get(ramdomNumber.ramdomInteger(epoch.getData().size()));
    }
    @Test(description = "compare api get list epoch with api get detail epoch", groups = {"epoch", "epoch_scenario"}, priority = 1)
    public void compareGetAllEpochWithGetDetailEpoch(){
        EpochData epochDetailModel = (EpochData)
                epochSteps.getEpochByEpochNo(epochData.getNo())
                        .validateResponse(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(EpochData.class);
        epochSteps.compareAllEpochWithDetailEpoch(epochData, epochDetailModel);
    }
    @Test(description = "compare api list epoch with api get current epoch", groups = {"epoch", "epoch_scenario"}, priority = 1)
    public void compareGetAllEpochWithCurrentEpoch(){
        EpochCurrent epochCurrent = (EpochCurrent)
        epochSteps.getCurrentEpoch()
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(EpochCurrent.class);
        epochSteps.compareListEpochWithCurrentEpoch(listEpoch, epochCurrent);
    }
    @Test(description = "compare api get list epoch block with api get detail epoch", groups = {"epoch", "epoch_scenario"}, priority = 1)
    public void compareGetAllEpochBlockWithGetDetailEpoch(){
        EpochByEpochNo epochByEpochNo = (EpochByEpochNo)
                epochSteps.getBLockListEpochByEpochNo(epochData.getNo())
                        .validateResponse(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(EpochByEpochNo.class);
        EpochData epochDetailModel = (EpochData)
                epochSteps.getEpochByEpochNo(epochData.getNo())
                        .validateResponse(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(EpochData.class);
        epochSteps.compareEpochBlockWithDetailEpoch(epochByEpochNo, epochDetailModel);
    }
}
