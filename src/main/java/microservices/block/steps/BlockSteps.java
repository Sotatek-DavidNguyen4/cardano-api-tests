package microservices.block.steps;

import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.block.constants.BlockConstants;
import microservices.block.models.BlockDetailModel;
import microservices.block.models.BlockListModel;
import microservices.block.models.BlockListTxsModel;
import microservices.common.constants.RequestParams;
import microservices.common.steps.BaseSteps;
//import microservices.common.util.SortListUtil;
import org.testng.Assert;
import util.AttributeStandard;
import util.SortListUtil;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import static constants.DateFormats.DATE_FORMAT;
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
    public BlockSteps verifyValueOfAttributeIsCorrectly(BlockListModel blockListModel, Object value) {
        if(blockListModel.getCurrentPage() <= 0){
            Assert.assertEquals(String.valueOf(blockListModel.getCurrentPage()), "0");
        }else{
            Assert.assertEquals(String.valueOf(blockListModel.getCurrentPage()), value);
        }
        return this;
    }

    @Step("Verify filter block")
    public BlockSteps then_verifyFilterBlockResponse(BlockListModel blockListModel, Map<String, Object> params) {
        RequestParams requestParams = new RequestParams(params,0,20);
        assertThat(blockListModel.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        assertThat(blockListModel.getData().size())
                .as("The size of page is wrong")
                .isEqualTo(requestParams.getSize());
        if (requestParams.getSort() != null) {
            boolean sorted = SortListUtil.isSortedByField(new ArrayList<>(blockListModel.getData()), requestParams.getSort());
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
    @Step("Verify value format is correctly")
    public BlockSteps then_verifyValueFormatIsCorrectly(BlockListModel blockListModel) {
        Assert.assertTrue(AttributeStandard.areValidHashes(blockListModel.getData().stream().map(s->s.getHash()).collect(Collectors.toList())),"Hash format is wrong");
        Assert.assertTrue(AttributeStandard.areValidDates(blockListModel.getData().stream().map(s -> s.getId()).collect(Collectors.toList()),DATE_FORMAT[2]),"time format is wrong");
        if(blockListModel.getData().stream().filter(s -> s.getBlockHash() != null).count() != 0){
            Assert.assertTrue(AttributeStandard.areValidBlockHashs(blockListModel.getData().stream().map(s -> s.getBlockHash()).collect(Collectors.toList())),"block hash is wrong");
        }
        return this;
    }
    @Step("Verify value format is correctly")
    public BlockSteps then_verifyValueFormatIsCorrectly(BlockDetailModel blockDetailModel) {
        Assert.assertTrue(AttributeStandard.isValidHash(blockDetailModel.getHash()),"Hash format is wrong");
        Assert.assertTrue(AttributeStandard.isValidDateFormat(blockDetailModel.getId(),DATE_FORMAT[0]),"time format is wrong");
        return this;
    }
    @Step("compare api get all block with api get a block detail")
    public BlockSteps compareResponseGetAllBlockAndGetDetailBlock(BlockDetailModel blockDetailInListBlock, BlockDetailModel blockDetail){
        assertThat(blockDetailInListBlock.getHash()).isEqualTo(blockDetail.getHash());
        assertThat(blockDetailInListBlock.getId()).isEqualTo(blockDetail.getId());
        assertThat(blockDetailInListBlock.getTxCount()).isEqualTo(blockDetail.getTxCount());
        assertThat(blockDetailInListBlock.getTotalFees()).isEqualTo(blockDetail.getTotalFees());
        assertThat(blockDetailInListBlock.getTotalOutput()).isEqualTo(blockDetail.getTotalOutput());
        return this;
    }
    @Step("compare api get tx list block with api get a block detail")
    public BlockSteps compareResponseGetTxListBlockAndGetDetailBlock(BlockListTxsModel blockListTxsModel, BlockDetailModel blockDetailModel){
        assertThat(blockListTxsModel.getData().size()).isEqualTo(blockDetailModel.getTxCount());
        return this;
    }
}
