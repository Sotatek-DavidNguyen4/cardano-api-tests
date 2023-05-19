package tests.epoch;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import microservices.epoch.models.epoch.Epoch;
import microservices.epoch.models.EpochCurrent;
import microservices.epoch.models.epochByEpochNo.EpochByEpochNo;
import microservices.epoch.models.epochByEpochNo.EpochDataByEpochNo;
import microservices.epoch.steps.EpochSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Epic("cardano")
@Feature("api-epoch")
@Story("GET: Get block list of epoch by its no")
public class EpochNoBlockTests extends BaseTest {
    private EpochSteps epochSteps = new EpochSteps();
    private EpochByEpochNo epochByEpochNo = new EpochByEpochNo();
    private EpochCurrent epochCurrent ;
    private List<EpochDataByEpochNo> epochDataByEpochNo ;
    @Test(description = "Verify get block list of epoch by its no successfully" ,groups = {"epoch"},dataProvider = "dataGetListEpochByEpochNo")
    public void getBlockListEpochByNo(Integer epochNo){
        epochByEpochNo = (EpochByEpochNo) epochSteps.getBLockListEpochByEpochNo(epochNo)
                                  .validateResponse(HttpURLConnection.HTTP_OK)
                                  .saveResponseObject(EpochByEpochNo.class);

        epochDataByEpochNo = epochByEpochNo.getData();
        epochSteps.verifyValueEpochNoInBlock(epochDataByEpochNo,epochNo);
        epochSteps.verifyResponseEpochNoInBlockNotNull(epochDataByEpochNo);
    }
    @DataProvider(name = "dataGetListEpochByEpochNo")
    public Object[][] dataGetListEpochByEpochNo(){
        return new Object[][]{
                {30},
                {68}
        };
    }
    @Test(description = "Verify get block list of epoch by next epoch" ,groups = {"epoch"})
    public void getBlockListEpochByNextEpoch(){
        epochCurrent =epochSteps.getCurrentEpoch().saveResponseCurrentEpoch();

        epochByEpochNo = (EpochByEpochNo) epochSteps.getBLockListEpochByEpochNo(epochCurrent.getNo()+1)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(EpochByEpochNo.class);
        epochSteps.verifyResponseEpochByNextEpoch(epochByEpochNo,true,0,0,0);
    }
    @Test(description = "Verify get block list of epoch by current epoch" ,groups = {"epoch"})
    public void getBlockListEpochByCurrentEpoch(){
        epochCurrent =epochSteps.getCurrentEpoch().saveResponseCurrentEpoch();

        epochByEpochNo = (EpochByEpochNo) epochSteps.getBLockListEpochByEpochNo(epochCurrent.getNo())
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(EpochByEpochNo.class);
        epochDataByEpochNo = epochByEpochNo.getData();
        epochSteps.verifyValueEpochNoInBlock(epochDataByEpochNo,epochCurrent.getNo());
        epochSteps.verifyResponseEpochNoInBlockNotNull(epochDataByEpochNo);
    }

    @Test(description = "Verify get block list of epoch by its no with param successfully" ,groups = {"epoch"},dataProvider = "dataGetListEpochByEpochNoWithParam")
    public void getBlockListEpochByNoWithParam(Integer no,Object page,Object size,String sort){
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .withSort(sort)
                .getParamsMap();

        epochByEpochNo = (EpochByEpochNo) epochSteps.getBLockListEpochByEpochNoWithParam(param,no)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(EpochByEpochNo.class);
        epochDataByEpochNo = epochByEpochNo.getData();
        epochSteps.verifyValueEpochNoInBlock(epochDataByEpochNo,no)
                  .verifyResponseEpochNoInBlockNotNull(epochDataByEpochNo)
                  .verifyNumberPage(epochByEpochNo.getCurrentPage(),page)
                  .verifySizeOfResponse(epochDataByEpochNo.size(),size)
                  .verifyResponseByEpochNoIsSorted(sort,epochDataByEpochNo);
    }
    @DataProvider(name = "dataGetListEpochByEpochNoWithParam")
    public Object[][] dataGetListEpochByEpochNoWithParam(){
        return new Object[][]{
                {60,6,null,null},
                {60,"a",null,null},
                {60,-6,null,null},
                {60," ",null,null},
                {60,"(jnfj#$%)",null,null},
                {60,null,1,null},
                {60,null,"a",null},
                {60,null,-2,null},
                {60,null," ",null},
                {60,null,"(jnfj#$%)",null},
                {60,null,null,"id,DESC"},
                {60,null,null,"id,ASC"},
        };
    }
}
