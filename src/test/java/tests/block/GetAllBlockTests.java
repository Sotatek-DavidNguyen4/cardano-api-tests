package tests.block;

import base.BaseTest;
import constants.enums.AnalyticsType;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.block.models.BlockModels;
import microservices.block.steps.BockSteps;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateMultiParameters;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

@Epic("cardano")
@Feature("api-block")
public class GetAllBlockTests extends BaseTest {
    BockSteps bockSteps = new BockSteps();
    @Test(description = "Get all blocks successfully", groups = {"block", "get-all-blocks"}, dataProvider = "paramData")
    public void getAllBlockSuccessfully(Object page, Object size, Object sort){

        BlockModels blockModels = (BlockModels) bockSteps
                .getAllBlock()
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockModels.class);

        //verify currentPage = 0;
        bockSteps.verifyValueOfAttributeIsCorrectly(blockModels, 0);


        MultiMap param = new CreateMultiParameters()
                .withPage(page)
                .withPageSize(size)
                .withSort(sort)
                .getParamsMap();

        blockModels = (BlockModels) bockSteps
                .getAllBlock(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockModels.class);

        bockSteps.then_verifyFilterBlockResponse(blockModels, param);
    }
    @DataProvider(name ="paramData")
    public Object[][] dataSetData(){
        return new Object[][]{
                {"0","20","id,DESC"}
        };
    }
    @Test(description = "Get all blocks successfully with page", groups = {"block", "get-all-blocks"}, dataProvider = "paramWithPage")
    public void getAllBlockSuccessfullyWithPage(Object page){

        MultiMap param = new CreateMultiParameters()
                .withPageSize(page)
                .getParamsMap();

        BlockModels blockModels = (BlockModels) bockSteps
                .getAllBlock(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockModels.class);

        bockSteps.verifyValueOfAttributeIsCorrectly(blockModels, page);
    }
    @DataProvider(name ="paramWithSize")
    public Object[][] dataSetWithSize(){
        return new Object[][]{
                {"1"},
                {"abc"},
                {"-10"},
                {" "},
                {"@#$%%"},
        };
    }
    @Test(description = "Get all blocks successfully with size", groups = {"block", "get-all-blocks"}, dataProvider = "paramWithSize")
    public void getAllBlockSuccessfullyWithSize(Object size){

        MultiMap param = new CreateMultiParameters()
                .withPageSize(size)
                .getParamsMap();

        BlockModels blockModels = (BlockModels) bockSteps
                .getAllBlock(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockModels.class);
    }
    @DataProvider(name ="paramWithPage")
    public Object[][] dataSetWithPage(){
        return new Object[][]{
                {"10"},
                {"abc"},
                {"-10"},
                {" "},
                {"@#$%%"},
        };
    }
    @Test(description = "Get all blocks successfully with sort", groups = {"block", "get-all-blocks"}, dataProvider = "paramWithSort")
    public void getAllBlockSuccessfullyWithSort(Object sort){

        MultiMap param = new CreateMultiParameters()
                .withSort(sort)
                .getParamsMap();

        BlockModels blockModels = (BlockModels) bockSteps
                .getAllBlock(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockModels.class);

        bockSteps.then_verifyFilterBlockResponse(blockModels, param);
    }
    @DataProvider(name ="paramWithSort")
    public Object[][] dataSetWithSort(){
        return new Object[][]{
                {"id,DESC"},
                {"id,ASC"},
        };
    }
}
