package microservices.block.steps;

import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.block.constants.BlockConstants;
import microservices.block.models.BlockListModel;
import microservices.block.models.BlockModels;
import microservices.common.constants.RequestParams;
import microservices.common.steps.BaseSteps;
//import microservices.common.util.SortListUtil;
import org.testng.Assert;
import util.SortListUtil;

import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BlockSteps extends BaseSteps {
    @Step("get all block")
    public BlockSteps getAllBlock() {
        sendGet(Endpoints.BlockApi.BLOCK_URI);
        return this;
    }

    @Step("get all block")
    public BlockSteps getAllBlock(Map<String, Object> param) {
        sendGet(Endpoints.BlockApi.BLOCK_URI, param);
        return this;
    }
    @Step("get a block detail")
    public BlockSteps getABlockDetail(Object blockId) {
        sendGet(Endpoints.BlockApi.BLOCK_DETAIL_URI, BlockConstants.BOCK_ID, blockId);
        return this;
    }
    @Step("get a list of block")
    public BlockSteps getTxListOfBlock(Object blockId) {
        sendGet(Endpoints.BlockApi.BLOCK_LIST_URI,BlockConstants.BOCK_ID, blockId);
        return this;
    }
    @Step("get a list of block")
    public BlockSteps getTxListOfBlock(Object blockId, Map<String, Object> param) {
        sendGet(Endpoints.BlockApi.BLOCK_LIST_URI,param ,BlockConstants.BOCK_ID, blockId);
        return this;
    }

    @Step("verify value of attribute is correctly")
    public BlockSteps verifyValueOfAttributeIsCorrectly(BlockModels blockModels, Object value) {
        if(blockModels.getCurrentPage() <= 0){
            Assert.assertEquals(String.valueOf(blockModels.getCurrentPage()), "0");
        }else{
            Assert.assertEquals(String.valueOf(blockModels.getCurrentPage()), value);
        }
        return this;
    }

    @Step("Verify filter block")
    public BlockSteps then_verifyFilterBlockResponse(BlockModels blockModels, Map<String, Object> params) {
        RequestParams requestParams = new RequestParams(params,0,20);
        assertThat(blockModels.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        assertThat(blockModels.getData().size())
                .as("The size of page is wrong")
                .isEqualTo(requestParams.getSize());
        if (requestParams.getSort() != null) {
            boolean sorted = SortListUtil.isSortedByField(new ArrayList<>(blockModels.getData()), requestParams.getSort());
            assertThat(sorted).as("block is not sorted by inputted params").isEqualTo(true);
        }
        return this;
    }
    @Step("Verify data null")
    public BlockSteps then_verifyDataNull(BlockListModel blockListModel) {
        Assert.assertTrue(blockListModel.getData().isEmpty(),"data null");
        return this;
    }
    @Step("Verify data not null")
    public BlockSteps then_verifyDataNotNull(BlockListModel blockListModel) {
        Assert.assertFalse(blockListModel.getData().isEmpty(),"data not null");
        return this;
    }
}
