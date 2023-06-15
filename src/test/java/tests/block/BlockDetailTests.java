package tests.block;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.block.constants.BlockConstants;
import microservices.block.models.BlockDetailModel;
import microservices.block.steps.BlockSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;

import static data.ApiResponseData.*;

@Epic("cardano")
@Feature("api-block")
public class BlockDetailTests extends BaseTest {
    BlockSteps blockSteps = new BlockSteps();
    String blockId = "950010";

    @Test(description = "get a block detail successfully", groups = {"block","block-detail"})
    public void getABlockDetailSuccessfully(){
        BlockDetailModel blockDetailModel = (BlockDetailModel) blockSteps
                .getABlockDetail(blockId)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockDetailModel.class);
        // wait for setting up environment first
        blockSteps.then_verifyValueFormatIsCorrectly(blockDetailModel);
    }
    @Test(description = "get a block detail unsuccessfully", groups = {"block","block-detail"}, dataProvider = "paramUnsuccessful")
    public void getABlockDetailUnsuccessfully(Object blockId){
        blockSteps
                .getABlockDetail(blockId)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, BlockConstants.ERROR_MESSAGE, BlockConstants.ERROR_CODE);
    }
    @DataProvider(name ="paramUnsuccessful")
    public Object[][] dataSetInvalidBlockId(){
        return new Object[][]{
                {"stake_test1urf6y7ktcqwzxd2tc3x54437jl6vcqvazgrdka3v2njdjzgyn6hgy"},
                {"@#$"},
                {" "},
                {"123ab"},
        };
    }
    @Test(description = "get block detail by valid blockId", groups = {"block","block-detail"}, dataProvider = "responseWithDataBlockIdInPreprod")
    public void get_block_detail_by_valid_blockId(BlockDetailModel expectedResponse){
        if(System.getProperty("cardanoAPI.baseEnv").contains("preprod")){
            BlockDetailModel blockDetailModel = (BlockDetailModel)
                    blockSteps.getABlockDetail(expectedResponse.getBlockNo())
                            .validateStatusCode(HttpURLConnection.HTTP_OK)
                            .saveResponseObject(BlockDetailModel.class);
            blockSteps.then_verifyBlockResponseWithDataTest(blockDetailModel, expectedResponse);
        }
    }
    @Test(description = "get block detail by valid blockId in mainnet", groups = {"block","block-detail"}, dataProvider = "responseWithDataBlockIdMainnet")
    public void get_block_detail_by_valid_blockId_mainnet(BlockDetailModel expectedResponse){
        if(System.getProperty("cardanoAPI.baseEnv").contains("mainnet")){
            BlockDetailModel blockDetailModel = (BlockDetailModel)
                    blockSteps.getABlockDetail(expectedResponse.getBlockNo())
                            .validateStatusCode(HttpURLConnection.HTTP_OK)
                            .saveResponseObject(BlockDetailModel.class);
            blockSteps.then_verifyBlockResponseWithDataTest(blockDetailModel, expectedResponse);
        }
    }
    @DataProvider(name ="responseWithDataBlockIdInPreprod")
    public Object[][] dataBlockIdInPreProd() {
        return new Object[][]{
                {FIRST_BLOCK},
                {BLOCK_MANY_TXNS},
                {BLOCK_A_TXN},
        };
    }
    @DataProvider(name ="responseWithDataBlockIdMainnet")
    public Object[][] dataBlockIdMainnet() {
        return new Object[][]{
                {BLOCK_BYRON_ERA},
                {BLOCK_SHELLY_ERA},
                {BLOCK_ALLEGRA_ERA},
                {BLOCK_MARY_ERA},
                {BLOCK_ALOZO_ERA},
                {BLOCK_BABBAGE_ERA},
        };
    }
}
