package tests.block;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.block.models.BlockListModel;
import microservices.block.models.BlockModels;
import microservices.block.steps.BlockSteps;
import org.apache.commons.collections.MultiMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateMultiParameters;

import java.net.HttpURLConnection;

@Epic("cardano")
@Feature("api-block")
public class BlockListTests extends BaseTest {
    BlockSteps blockSteps = new BlockSteps();
    String blockId = "950010";

    @Test(description = "get block list successfully", groups = {"block","block-list"})
    public void getBlockListSuccessfully(){
        BlockListModel blockListModel = (BlockListModel) blockSteps
                .getTxListOfBlock(blockId)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockListModel.class);

        blockSteps.then_verifyDataNotNull(blockListModel);

        blockId = "950073";
        blockListModel = (BlockListModel) blockSteps
                .getTxListOfBlock(blockId)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockListModel.class);

        blockSteps.then_verifyDataNull(blockListModel);
    }
    @Test(description = "get block list unsuccessfully with invalid page", groups = {"block","block-list"}, dataProvider = "paramInvalidPage")
    public void getBlockListUnsuccessfullyWithInvalidPage(Object page){
        MultiMap param = new CreateMultiParameters()
                .withPage(page)
                .getParamsMap();
        BlockListModel blockListModel = (BlockListModel) blockSteps
                .getTxListOfBlock(blockId, param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockListModel.class);

        blockSteps.then_verifyDataNotNull(blockListModel);

    }
    @DataProvider(name ="paramInvalidPage")
    public Object[][] dataSetInvalidPage(){
        return new Object[][]{
                {"abc"},
                {"-10"},
                {" "},
                {"@#$%%"},
        };
    }
    @Test(description = "get block list unsuccessfully with invalid blockId", groups = {"block","block-list"}, dataProvider = "paramInvalidBlockId")
    public void getBlockListUnsuccessfullyWithInvalidBlockId(Object blockId){
        BlockListModel blockListModel = (BlockListModel) blockSteps
                .getTxListOfBlock(blockId)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockListModel.class);

        blockSteps.then_verifyDataNull(blockListModel);

    }
    @DataProvider(name ="paramInvalidBlockId")
    public Object[][] dataSetInvalidBlockId(){
        return new Object[][]{
                {"@#$"},
                {" "},
                {"abcd"},
                {"12345"},
        };
    }
    @Test(description = "get block list unsuccessfully with invalid size", groups = {"block","block-list"}, dataProvider = "paramInvalidSize")
    public void getBlockListUnsuccessfullyWithInvalidSize(Object size){
        MultiMap param = new CreateMultiParameters()
                .withPageSize(size)
                .getParamsMap();
        BlockListModel blockListModel = (BlockListModel) blockSteps
                .getTxListOfBlock(blockId, param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockListModel.class);

        blockSteps.then_verifyDataNotNull(blockListModel);

    }
    @DataProvider(name ="paramInvalidSize")
    public Object[][] dataSetInvalidSize(){
        return new Object[][]{
                {"1"},
                {"y"},
                {"-10"},
                {" "},
                {"!@@$$"},
        };
    }
}
