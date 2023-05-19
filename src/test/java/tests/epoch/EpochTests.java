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
        Map<String,Object> paramsMap = new CreateParameters().withPage(page)
                                                             .withPageSize(size)
                                                             .withSort(sort)
                                                             .getParamsMap();
        epoch = (Epoch) epochSteps.getListEpoch(paramsMap)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(Epoch.class);

        epochData = epoch.getData();
        epochSteps.verifyCurrentPage(epoch,page)
                  .verifyResponseEpochNotNull(epochData)
                .verifyResponseIsSorted(sort,epochData);
    }
    @DataProvider(name = "dataGetListEpoch")
    public Object[][] dataGetListEpoch(){
        return new Object[][]{
                {null,null,null},
                {1,5,"id,asc"},
                {4,10,"id,asc"},
                {2,10,"id,asc"},
                {6,null,null},
                {"a",null,null},
                {-6,null,null},
                {" ",null,null},
                {"(jnfj#$%)",null,null},
                {null,1,null},
                {null,"a",null},
                {null,-2,null},
                {null," ",null},
                {null,"(jnfj#$%)",null},
                {null,null,"id,desc"},
                {null,null,"id,ASC"},
                {null,null,"outSum,desc"},
//                {null,null,"outSum,ASC"},

/**
 *Pending in TC API_2.31
*/
        };
    }

}
