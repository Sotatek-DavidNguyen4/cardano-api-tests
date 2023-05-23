package tests.epoch;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import microservices.common.constants.APIErrorCode;
import microservices.common.constants.APIErrorMessage;
import microservices.common.models.ErrorResponse;
import microservices.epoch.models.EpochCurrent;
import microservices.epoch.models.epoch.Epoch;
import microservices.epoch.models.epoch.EpochData;
import microservices.epoch.models.epochByEpochNo.EpochByEpochNo;
import microservices.epoch.models.epochByEpochNo.EpochDataByEpochNo;
import microservices.epoch.steps.EpochSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

@Epic("cardano")
@Feature("api-epoch")
@Story("GET: Get a epoch detail by its no")
public class EpochNoTests extends BaseTest {
    private EpochSteps epochSteps = new EpochSteps();
    private EpochData epochData = new EpochData();
    private EpochCurrent epochCurrent ;

    @Test(description = "Verify get epoch by its no successfully" ,groups = {"epoch"})
    public void getEpochByNo(){
        epochData = (EpochData) epochSteps.getEpochByEpochNo(60)
                                  .validateResponse(HttpURLConnection.HTTP_OK)
                                  .saveResponseObject(EpochData.class);

        epochSteps.verifyValueEpochNo(epochData,60)
                  .verifyValueMaxSlot(epochData,432000)
                  .verifyResponseEpochNoNotNull(epochData);
    }

    @Test(description = "Verify get epoch by current epoch successfully" ,groups = {"epoch"})
    public void getEpochByCurrentNo(){
        epochCurrent =(EpochCurrent) epochSteps.getCurrentEpoch().saveResponseObject(EpochCurrent.class);

        epochData = (EpochData) epochSteps.getEpochByEpochNo(epochCurrent.getNo())
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(EpochData.class);

        epochSteps.verifyValueEpochNo(epochData,epochCurrent.getNo())
                .verifyValueMaxSlot(epochData,432000)
                .verifyResponseEpochNoNotNull(epochData);
    }

    @Test(description = "Verify get epoch by epoch no Unsuccessfully" ,groups = {"epoch"},dataProvider = "dataGetEpochByNo")
    public void getEpochByNoUnSuccess(Object no){
        epochSteps.getEpochByEpochNo(no)
                  .then_verifyErrorResponse(400, APIErrorMessage.EPOCH_NOT_FOUND, APIErrorCode.EPOCH_NOT_FOUND);

    }
    @DataProvider(name = "dataGetEpochByNo")
    public Object[][] dataGetListEpochByEpochNo(){
        return new Object[][]{
                {"8e0280beebc3d12626e87b182f4205d75e49981042f54081cd35f3a4a85630b0"},
                {"a"},
                {-1},
                {"@$"},
        };
    }
    /**
     * case blank not automated
     */
}
