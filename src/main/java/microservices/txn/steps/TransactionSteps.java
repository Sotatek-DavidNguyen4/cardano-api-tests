package microservices.txn.steps;

import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.common.constants.RequestParams;
import microservices.common.steps.BaseSteps;
//import microservices.txn.constants.Transaction;
import util.AttributeStandard;
import util.DateUtil;
import util.SortListUtil;
import microservices.txn.models.*;
import org.testng.Assert;

import java.util.*;
import java.util.stream.Collectors;

import static constants.DateFormats.DATE_FORMAT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.Assert.assertTrue;

public class TransactionSteps extends BaseSteps {

    @Step("Get transaction by hash")
    public TransactionSteps when_getTransactionByHash(String hash) {
        sendGet(Endpoints.TransactionApi.TRANSACTION_HASH, Endpoints.TransactionApi.HASH_ID, hash);
        return this;
    }

    @Step("Verify transaction response")
    public TransactionSteps then_verifyTransactionResponse(TransactionResponse txnResponse, String hash) {
        assertThat(txnResponse.getTx().getHash())
                .as("Value of field 'tx.hash' is wrong")
                .isEqualTo(hash);
        assertTrue(AttributeStandard.isValidDateFormat(txnResponse.getTx().getTime(), DATE_FORMAT[0]));
        assertTrue(AttributeStandard.isValidHash(txnResponse.getTx().getHash()));
        assertTrue(AttributeStandard.isValidBlockHash(txnResponse.getTx().getBlockHash()));
        if (txnResponse.getTx().getHash() == "5526b1373acfc774794a62122f95583ff17febb2ca8a0fe948d097e29cf99099") {
            assertThat(txnResponse.getTx().getOutSum())
                    .as("Value of field 'tx.totalOutput' is wrong")
                    .isEqualTo("30000000000000000");
        }
        return this;
    }

    @Step("Get filter transaction")
    public TransactionSteps when_filterTransaction(Map<String, Object> params) {
        sendGet(Endpoints.TransactionApi.FILTER_TRANSACTION, params);
        return this;
    }

    @Step("Verify filter transaction")
    public TransactionSteps then_verifyFilterTransactionResponse(FilterTransactionResponse filterTxsRes, Map<String, Object> params) {
        RequestParams requestParams = new RequestParams(params, 0, 20);
        assertThat(filterTxsRes.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        assertThat(filterTxsRes.getData().size())
                .as("The size of page is wrong")
                .isEqualTo(requestParams.getSize());
        if (requestParams.getSort()!=null) {
            boolean sorted = SortListUtil.isSortedByField(new ArrayList<>(filterTxsRes.getData()), requestParams.getSort());
            assertThat(sorted).as("Transaction is not sorted by inputted params").isEqualTo(true);
        assertTrue(AttributeStandard.areValidHashes(filterTxsRes.getData().stream().map(s -> s.getBlockHash()).collect(Collectors.toList())));
        assertTrue(AttributeStandard.areValidDates(filterTxsRes.getData().stream().map(s -> s.getTime()).collect(Collectors.toList()), DATE_FORMAT[0]));
        }

        return this;
    }

    @Step("Get number transaction on fixable days")
    public TransactionSteps when_getTransactionOnFixableDays(String type) {
        sendGet(Endpoints.TransactionApi.TRANSACTION_GRAPH, Endpoints.TransactionApi.TYPE, type);
        return this;
    }

    @Step("Get current transaction")
    public TransactionSteps when_getCurrentTransaction() {
        sendGet(Endpoints.TransactionApi.TRANSACTION_CURRENT);
        return this;
    }

    @Step("Get current transaction")
    public TransactionSteps then_verifyCurrentTransactionResponse(List<Transaction> currentTransactionsList) {
        Assert.assertEquals(currentTransactionsList.size(),4);
        return this;
    }

    @Step("Verify transaction graph response")
    public TransactionSteps then_verifyTypeTransactionResponse(List<TransactionGraphResponse> transactionGraphResponseList, int day) {
        String endDate = DateUtil.getCurrentUTCDate(DATE_FORMAT[1]);
        String startDate = DateUtil.getCurrentUTCSubDays(day, DATE_FORMAT[1]);
        for (TransactionGraphResponse  txnGraph : transactionGraphResponseList) {
            assertTrue(DateUtil.compareDurations(txnGraph.getDate(), startDate, endDate, DATE_FORMAT[1]),
                    txnGraph.getDate() + " not true");
        }
        assertTrue(AttributeStandard.areValidDates(transactionGraphResponseList.stream().map(s -> s.getDate()).collect(Collectors.toList()), DATE_FORMAT[0]));
        return this;
    }

    @Step("Verify transaction response")
    public TransactionSteps then_verifyTransactionResponseWithDataTest(TransactionResponse txnResponse, TransactionResponse responseExpected) {
        assertThat(txnResponse.getTx().getHash())
                .as("Value of field 'tx.hash' is wrong")
                .isEqualTo(responseExpected.getTx().getHash());
        assertThat(txnResponse.getTx().getBlockNo())
                .as("Value of field 'tx.blockNo' is wrong")
                .isEqualTo(responseExpected.getTx().getBlockNo());
        assertThat(txnResponse.getTx().getBlockHash())
                .as("Value of field 'tx.blockHash' is wrong")
                .isEqualTo(responseExpected.getTx().getBlockHash());
        assertThat(txnResponse.getTx().getMaxEpochSlot())
                .as("Value of field 'tx.maxEpochSlot' is wrong")
                .isEqualTo(responseExpected.getTx().getMaxEpochSlot());
        assertThat(txnResponse.getTx().getEpochNo())
                .as("Value of field 'tx.epochNo' is wrong")
                .isEqualTo(responseExpected.getTx().getEpochNo());
        assertThat(txnResponse.getSummary())
                .as("Value of field 'tx.summary' is wrong")
                .isEqualTo(responseExpected.getSummary());
        assertThat(txnResponse.getContracts())
                .as("Value of field 'tx.contracts' is wrong")
                .isEqualTo(responseExpected.getContracts());
        assertThat(txnResponse.getCollaterals())
                .as("Value of field 'tx.collaterals' is wrong")
                .isEqualTo(responseExpected.getCollaterals());
        return this;
    }
}
