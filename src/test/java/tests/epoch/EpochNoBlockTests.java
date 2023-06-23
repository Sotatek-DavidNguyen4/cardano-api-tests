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
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
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
        String blockListSchema = "schemaJson/epoch/blockListOfEpochByNo.json";
        epochByEpochNo = (EpochByEpochNo) epochSteps.getBLockListEpochByEpochNo(epochNo)
                                  .validateResponse(HttpURLConnection.HTTP_OK)
                                  .validateResponseSchema(blockListSchema)
                                  .saveResponseObject(EpochByEpochNo.class);

        epochDataByEpochNo = epochByEpochNo.getData();
        epochSteps.verifyValueEpochNoInBlock(epochDataByEpochNo,epochNo)
                  .verifyResponseEpochNoInBlockNotNull(epochDataByEpochNo)
                  .then_verifyFormatOfBlockListResponse(epochDataByEpochNo);
    }
    @DataProvider(name = "dataGetListEpochByEpochNo")
    public Object[][] dataGetListEpochByEpochNo(){
        return new Object[][]{
                {73},
                {79}
        };
    }
    @Test(description = "Verify get block list of epoch by next epoch" ,groups = {"epoch"})
    public void getBlockListEpochByNextEpoch(){
        epochCurrent =(EpochCurrent) epochSteps.getCurrentEpoch().saveResponseObject(EpochCurrent.class);

        epochByEpochNo = (EpochByEpochNo) epochSteps.getBLockListEpochByEpochNo(epochCurrent.getNo()+1)
                                                    .validateResponse(HttpURLConnection.HTTP_OK)
                                                    .saveResponseObject(EpochByEpochNo.class);
        epochSteps.verifyResponseEpochByNextEpoch(epochByEpochNo,true,0,0,0);
    }
    @Test(description = "Verify get block list of epoch by current epoch" ,groups = {"epoch"})
    public void getBlockListEpochByCurrentEpoch(){
        epochCurrent =(EpochCurrent) epochSteps.getCurrentEpoch().saveResponseObject(EpochCurrent.class);

        epochByEpochNo = (EpochByEpochNo) epochSteps.getBLockListEpochByEpochNo(epochCurrent.getNo())
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(EpochByEpochNo.class);
        epochDataByEpochNo = epochByEpochNo.getData();
        epochSteps.verifyValueEpochNoInBlock(epochDataByEpochNo,epochCurrent.getNo())
                  .verifyResponseEpochNoInBlockNotNull(epochDataByEpochNo)
                  .then_verifyFormatOfBlockListResponse(epochDataByEpochNo);
    }

    @Test(description = "Verify get block list of epoch by its no with param successfully" ,groups = {"epoch"},dataProvider = "dataGetListEpochByEpochNoWithParam")
    public void getBlockListEpochByNoWithParam(Integer no,String page,String size,String sort){
        MultiMap param = new MultiValueMap();
        param.put("page", page);
        param.put("size", size);
        if(!sort.equals("")){
            param.put("sort", sort);
        }
        epochByEpochNo = (EpochByEpochNo) epochSteps.getBLockListEpochByEpochNoWithParam(param,no)
                                                    .validateResponse(HttpURLConnection.HTTP_OK)
                                                    .saveResponseObject(EpochByEpochNo.class);
        epochDataByEpochNo = epochByEpochNo.getData();
        epochSteps.verifyValueEpochNoInBlock(epochDataByEpochNo,no)
                  .verifyResponseEpochNoInBlockNotNull(epochDataByEpochNo)
                  .then_verifyEpochByNoBlockResponse(epochByEpochNo,param)
                  .then_verifyFormatOfBlockListResponse(epochDataByEpochNo);
    }
    @DataProvider(name = "dataGetListEpochByEpochNoWithParam")
    public Object[][] dataGetListEpochByEpochNoWithParam(){
        return new Object[][]{
                {79,"6","",""},
                {79,"a","",""},
                {79,"-6","",""},
                {79," ","",""},
                {79,"jnfj#$%","",""},
                {79,"","1",""},
                {79,"","a",""},
                {79,"","-2",""},
                {79,""," ",""},
                {79,"","jnfj#$%",""},
        };
    }
}
