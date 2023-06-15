package tests.transaction;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.block.models.BlockListModel;
import microservices.block.steps.BlockSteps;
import microservices.common.constants.APIErrorCode;
import microservices.common.constants.APIErrorMessage;
import microservices.txn.models.FilterTransactionResponse;
import microservices.txn.models.Transaction;
import microservices.txn.models.TransactionGraphResponse;
import microservices.txn.models.TransactionResponse;
import microservices.txn.steps.TransactionSteps;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.util.List;

import static data.ApiResponseData.*;

@Epic("cardano")
@Feature("api-transactions")
public class TransactionScenarioTests extends BaseTest {
    TransactionSteps txnSteps = new TransactionSteps();
    BlockSteps blockSteps = new BlockSteps();
    private TransactionResponse txnResponseByHash;

    @Test(description = "Compare api: Filter transaction with api: Get transaction detail by hash", groups = "transactions")
    public void compare_filter_transaction_with_transaction_detail_by_hash() {
        MultiMap params = new MultiValueMap();
        params.put("page", null);
        params.put("size", null);
        FilterTransactionResponse filterTxsRes = (FilterTransactionResponse) txnSteps.when_filterTransaction(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(FilterTransactionResponse.class);

        String hash = filterTxsRes.getData().get(0).getHash();
        txnResponseByHash = (TransactionResponse) txnSteps.when_getTransactionByHash(hash)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TransactionResponse.class);

        txnSteps.then_verifyTransactionResponseFilterANdHash(filterTxsRes,txnResponseByHash);
    }
    @Test(description = "Compare api: Get current transactions (Latest Transactions of Dashboard) with api: Filter transaction", groups = "transactions")
    public void compare_current_transaction_with_filter_transaction() {
        List<Transaction> currentTransactionsList = txnSteps.when_getCurrentTransaction()
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseListObject(Transaction[].class);
        txnSteps.then_verifyCurrentTransactionResponse(currentTransactionsList);

        MultiMap params = new MultiValueMap();
        params.put("page", null);
        params.put("size", null);
        FilterTransactionResponse filterTxsRes = (FilterTransactionResponse) txnSteps.when_filterTransaction(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(FilterTransactionResponse.class);
        txnSteps.then_verifyFilterTransactionResponse(filterTxsRes, params);

        txnSteps.then_verifyTransactionResponseFilterAndCurrent(filterTxsRes,currentTransactionsList);
    }

    @Test(description = "Compare api: Get transaction detail by hash with api: Get tx list of block", groups = "transactions")
    public void compare_transaction_by_hash_with_tx_list_of_block() {
        String hash = "dbc87d198fc9f6067c8e65f89da60a944e1ef52f0c539d980ebbc95f3dc71b65";
        txnResponseByHash = (TransactionResponse) txnSteps.when_getTransactionByHash(hash)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TransactionResponse.class);
        txnSteps.then_verifyTransactionResponse(txnResponseByHash, hash);

        BlockListModel blockListModel = (BlockListModel) blockSteps
                .getTxListOfBlock(txnResponseByHash.getTx().getBlockNo())
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(BlockListModel.class);

        blockSteps.then_verifyDataNotNull(blockListModel)
                  .then_verifyValueFormatIsCorrectly(blockListModel);

        txnSteps.then_verifyTransactionResponseByHashWithBlock(txnResponseByHash,blockListModel.getData().get(0));
    }
}
