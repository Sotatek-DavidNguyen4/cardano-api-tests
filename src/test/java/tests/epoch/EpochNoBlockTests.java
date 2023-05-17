package tests.epoch;

import base.BaseTest;
import microservices.epoch.models.Epoch;
import microservices.epoch.models.EpochCurrent;
import microservices.epoch.models.EpochData;
import microservices.epoch.steps.EpochSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Map;

public class EpochNoBlockTests extends BaseTest {
    private EpochSteps epochSteps = new EpochSteps();
    private Epoch epoch = new Epoch();
    private EpochCurrent epochCurrent ;
    private ArrayList<EpochData> epochData ;


    @Test(description = "Verify get block list of epoch by its no successfully" ,groups = {"epoch"},dataProvider = "dataGetListEpochByNo")
    public void getBlockListEpochByNo(int no,Object page){
//        Map<String,Object> param = new CreateParameters()
//                                        .withNo(no)
//                                        .withPage(page)
//                                        .getParamsMap();
        epochSteps.getBLockListEpochByNo(no)
                  .validateResponse(HttpURLConnection.HTTP_OK+2);
        epoch = epochSteps.saveResponseListEpoch();

    }

    @DataProvider(name = "dataGetListEpochByNo")
    public Object[][] dataGetListEpochByNo(){
        return new Object[][]{
                {30,"a"},
        };
    }
}
