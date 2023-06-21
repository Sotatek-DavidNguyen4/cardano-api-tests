package tests.block;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.block.models.BlockListModel;
import microservices.block.steps.BlockSteps;
import org.apache.commons.collections.MultiMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateMultiParameters;

import java.net.HttpURLConnection;

@Epic("cardano")
@Feature("api-block")
public class GetAllBlockTests extends BaseTest {
    BlockSteps bockSteps = new BlockSteps();
    @Test(description = "Get all blocks successfully", groups = {"block", "get-all-blocks"}, dataProvider = "paramData")
    public void getAllBlockSuccessfully(Object page, Object size, Object sort){

        BlockListModel blockListModel = (BlockListModel) bockSteps
                .getAllBlock()
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockListModel.class);

        //verify currentPage = 0;
        bockSteps
                .verifyValueOfAttributeIsCorrectly(blockListModel, 0)
                .then_verifyValueFormatIsCorrectly(blockListModel);


        MultiMap param = new CreateMultiParameters()
                .withPage(page)
                .withPageSize(size)
                .withSort(sort)
                .getParamsMap();

        blockListModel = (BlockListModel) bockSteps
                .getAllBlock(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockListModel.class);

        bockSteps
                .then_verifyFilterBlockResponse(blockListModel, param)
                .then_verifyValueFormatIsCorrectly(blockListModel);
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

        BlockListModel blockListModel = (BlockListModel) bockSteps
                .getAllBlock(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockListModel.class);

        bockSteps.verifyValueOfAttributeIsCorrectly(blockListModel, page);
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

        BlockListModel blockListModel = (BlockListModel) bockSteps
                .getAllBlock(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockListModel.class);
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

        BlockListModel blockListModel = (BlockListModel) bockSteps
                .getAllBlock(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockListModel.class);

        bockSteps.then_verifyFilterBlockResponse(blockListModel, param);
    }
    @DataProvider(name ="paramWithSort")
    public Object[][] dataSetWithSort(){
        return new Object[][]{
                {"id,DESC"},
                {"id,ASC"},
                {"txCount,desc"},
                {"txCount,ASC"},
        };
    }
}
