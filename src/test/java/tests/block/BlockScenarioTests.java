package tests.block;

import base.BaseTest;

import microservices.block.models.BlockDetailModel;
import microservices.block.models.BlockListModel;
import microservices.block.models.BlockListTxsModel;
import microservices.block.steps.BlockSteps;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.util.Random;

public class BlockScenarioTests extends BaseTest {
    private BlockSteps blockSteps = new BlockSteps();
    private BlockDetailModel blockInListBlock = new BlockDetailModel();
    private Random random = new Random();
    private int randomNumber = random.nextInt(20);
    @Test(description = "take block detail", priority = 0)
    public void takeBlockDetail(){
        BlockListModel blockListModel = (BlockListModel)
        blockSteps.getAllBlock()
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockListModel.class);
        blockInListBlock = blockListModel.getData().get(randomNumber);
    }
    @Test(description = "compare api get all block with api get block detail", groups = {"block", "block_scenario"}, priority = 1)
    public void compareGetAllBlockWithGetDetailBlock(){
        BlockDetailModel blockDetailModel = (BlockDetailModel)
        blockSteps.getABlockDetail(blockInListBlock.getBlockNo())
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockDetailModel.class);

        blockSteps.compareResponseGetAllBlockAndGetDetailBlock(blockInListBlock, blockDetailModel);
    }
    @Test(description = "compare api get tx list block with api get detail block", groups = {"block", "block_scenario"}, priority = 1)
    public void compareGetTxListBlockWithGetDetailBlock(){
        BlockListTxsModel blockListTxsModel = (BlockListTxsModel)
        blockSteps.getTxListOfBlock(blockInListBlock.getBlockNo())
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockListTxsModel.class);

        BlockDetailModel blockDetailModel = (BlockDetailModel)
                blockSteps.getABlockDetail(blockInListBlock.getBlockNo())
                        .validateResponse(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(BlockDetailModel.class);

        blockSteps.compareResponseGetTxListBlockAndGetDetailBlock(blockListTxsModel, blockDetailModel);
    }
}
