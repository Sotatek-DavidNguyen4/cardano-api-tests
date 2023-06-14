package tests.epoch;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import microservices.epoch.models.EpochCurrent;
import microservices.epoch.models.epoch.Epoch;
import microservices.epoch.models.epoch.EpochData;
import microservices.epoch.models.epochByEpochNo.EpochByEpochNo;
import microservices.epoch.models.epochByEpochNo.EpochDataByEpochNo;
import microservices.epoch.steps.EpochSteps;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

@Epic("cardano")
@Feature("api-epoch")
@Story("GET: Epoch All List")
public class EpochTests extends BaseTest {
    private EpochSteps epochSteps = new EpochSteps();
    private Epoch epoch = new Epoch();
    private List<EpochData> epochData ;
    @Test(description = "Verify get list of epoch successfully" ,groups = {"epoch"},dataProvider = "dataGetListEpoch")
    public void getBlockListEpochByNo(Object page,Object size,String sort){
        MultiMap param = new MultiValueMap();
        param.put("page", page);
        param.put("size", size);
        if(!sort.equals("")){
            param.put("sort", sort);
        }
        epoch = (Epoch) epochSteps.getListEpoch(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(Epoch.class);

        epochData = epoch.getData();
        epochSteps.verifyResponseEpochNotNull(epochData)
                  .then_verifyFormatOfEpochAllListResponse(epochData)
                  .then_verifyEpochResponse(epoch,param);
    }
    @DataProvider(name = "dataGetListEpoch")
    public Object[][] dataGetListEpoch(){
        return new Object[][]{
                //default
                {"","",""},
                //Check valid page,size,sort
                {"1","",""},
                {"","5",""},
//                {"","","id,asc"},
                //Check invalid page,size,sort
                {"a","",""},
                {"-6","",""},
                {" ","",""},
                {"jnfj#$%","",""},

                {"","a",""},
                {"","-2",""},
                {""," ",""},
                {"","jnfj#$%",""},

                {"","","outSum,desc"},
                {"","","outSum,ASC"},
                {"","","blkCount,desc"},
                {"","","blkCount,ASC"},
                
        };
    }

}
