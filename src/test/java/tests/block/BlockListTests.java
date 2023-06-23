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
public class BlockListTests extends BaseTest {
    BlockSteps blockSteps = new BlockSteps();
    private String blockId = "7249923";

    @Test(description = "get block list successfully", groups = {"block","block-list"})
    public void getBlockListSuccessfully(){
        String schemaJson = "schemaJson/block/block-list.json";
        int page = 10;
        BlockListModel blockListModel = (BlockListModel) blockSteps
                .getTxListOfBlock(blockId)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(schemaJson)
                .saveResponseObject(BlockListModel.class);

        blockSteps
                .then_verifyDataNotNull(blockListModel)
                .then_verifyValueFormatIsCorrectly(blockListModel);

        String blockId = "1376";
        blockListModel = (BlockListModel) blockSteps
                .getTxListOfBlock(blockId)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockListModel.class);

        blockSteps
                .then_verifyDataNull(blockListModel)
                .then_verifyValueFormatIsCorrectly(blockListModel);

        MultiMap param = new CreateMultiParameters()
                .withPage(page)
                .getParamsMap();

        blockListModel = (BlockListModel) blockSteps
                .getTxListOfBlock(blockId, param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockListModel.class);
        blockSteps.verifyThatDataResponseIsOnCorrectPage(10,blockListModel.getCurrentPage());

        page = blockListModel.getTotalPages();
        param = new CreateMultiParameters()
                .withPage(page)
                .getParamsMap();
        blockListModel = (BlockListModel) blockSteps
                .getTxListOfBlock(blockId, param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockListModel.class);
        blockSteps.verifyThatDataResponseIsOnCorrectPage(page ,blockListModel.getCurrentPage());
    }
    @Test(description = "get block list successfully with invalid page", groups = {"block","block-list"}, dataProvider = "paramInvalidPage")
    public void getBlockListSuccessfullyWithInvalidPage(Object page){
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
                {10},
                {"abc"},
                {-10},
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
    @Test(description = "get block list successfully with invalid size", groups = {"block","block-list"}, dataProvider = "paramInvalidSize")
    public void getBlockListSuccessfullyWithInvalidSize(Object size){
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
                {1},
                {"abc"},
                {-10},
                {" "},
                {"!@@$$"},
        };
    }
}
