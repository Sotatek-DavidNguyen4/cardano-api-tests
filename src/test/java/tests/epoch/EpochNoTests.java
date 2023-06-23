package tests.epoch;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import microservices.common.constants.APIErrorCode;
import microservices.common.constants.APIErrorMessage;
import microservices.epoch.models.EpochCurrent;
import microservices.epoch.models.epoch.EpochData;
import microservices.epoch.steps.EpochSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.net.HttpURLConnection;
import static data.ApiResponseData.*;

@Epic("cardano")
@Feature("api-epoch")
@Story("GET: Get a epoch detail by its no")
public class EpochNoTests extends BaseTest {
    private EpochSteps epochSteps = new EpochSteps();
    private EpochData epochData = new EpochData();
    private EpochCurrent epochCurrent ;

    @Test(description = "Verify get epoch by its no successfully" ,groups = {"epoch"})
    public void getEpochByNo(){

        String epochDetailByNoSchema = "schemaJson/epoch/epochDetailByNo.json";
        epochData = (EpochData) epochSteps.getEpochByEpochNo(EPOCH_BY_NO.getNo())
                                  .validateResponse(HttpURLConnection.HTTP_OK)
                                  .validateResponseSchema(epochDetailByNoSchema)
                                  .saveResponseObject(EpochData.class);

        epochSteps.then_verifyEpochResponseWithDataTest(epochData,EPOCH_BY_NO);
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

    @Test(description = "Verify get epoch by data no successfully" ,groups = {"epoch", "dataTest"},dataProvider = "responseWithDataEpochNo")
    public void getEpochByNoData(EpochData epochDataExpected){

            epochData = (EpochData) epochSteps.getEpochByEpochNo(epochDataExpected.getNo())
                    .validateResponse(HttpURLConnection.HTTP_OK)
                    .saveResponseObject(EpochData.class);

            epochSteps.verifyValueEpochNo(epochData,epochData.getNo())
                    .then_verifyFormatOfEpochDetailByNoResponse(epochData)
                    .then_verifyEpochResponseWithDataTest(epochData,epochDataExpected);
    }
    @DataProvider(name = "responseWithDataEpochNo")
    public Object[][] dataGetEpochByEpochNo(){
        String env = System.getProperty("cardanoAPI.baseEnv");
        Object[][] data ;
        if(env.equals("preprod")){
            data = new Object[][]{
                    {FIRST_EPOCH}
            };
        } else {
            data= new Object[][]{
                    {EPOCH_BYRON_ERA},
                    {EPOCH_SHELLY_ERA},
                    {EPOCH_BABBAGE_ERA},
                    {EPOCH_ALOZO_ERA},
                    {EPOCH_MARY_ERA},
                    {EPOCH_ALLEGRA_ERA}
            };
        }
        return data;
    }
}
