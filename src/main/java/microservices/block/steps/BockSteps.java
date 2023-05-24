package microservices.block.steps;

import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.addresses.steps.TopAddressSteps;
import microservices.block.models.BlockModels;
import microservices.common.constants.RequestParams;
import microservices.common.steps.BaseSteps;
import microservices.common.util.SortListUtil;
import microservices.txn.models.FilterTransactionResponse;
import microservices.txn.steps.TransactionSteps;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BockSteps extends BaseSteps {
    @Step("get all block")
    public BockSteps getAllBlock() {
        sendGet(Endpoints.BlockApi.BLOCK_URI);
        return this;
    }

    @Step("get all block")
    public BockSteps getAllBlock(Map<String, Object> param) {
        sendGet(Endpoints.BlockApi.BLOCK_URI, param);
        return this;
    }

    @Step("verify value of attribute is correctly")
    public BockSteps verifyValueOfAttributeIsCorrectly(BlockModels blockModels, Object value) {
        if(blockModels.getCurrentPage() <= 0){
            Assert.assertEquals(String.valueOf(blockModels.getCurrentPage()), "0");
        }else{
            Assert.assertEquals(String.valueOf(blockModels.getCurrentPage()), value);
        }
        return this;
    }

    @Step("Verify filter block")
    public BockSteps then_verifyFilterBlockResponse(BlockModels blockModels, Map<String, Object> params) {
        RequestParams requestParams = new RequestParams(params);
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
}
