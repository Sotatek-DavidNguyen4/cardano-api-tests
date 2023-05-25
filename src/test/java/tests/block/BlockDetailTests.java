package tests.block;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.block.constants.BlockConstants;
import microservices.block.steps.BlockSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;

@Epic("cardano")
@Feature("api-block")
public class BlockDetailTests extends BaseTest {
    BlockSteps blockSteps = new BlockSteps();
    String blockId = "950010";

    @Test(description = "get a block detail successfully", groups = {"block","block-detail"})
    public void getABlockDetailSuccessfully(){
        blockSteps
                .getABlockDetail(blockId)
                .validateStatusCode(HttpURLConnection.HTTP_OK);
        // wait for setting up environment first
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
                //{"@#$%"}, need dev fix this
                {" "},
                {"123ab"},
        };
    }
}
