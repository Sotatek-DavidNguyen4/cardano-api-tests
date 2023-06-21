package microservices.txn.steps;

import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.block.models.BlockDetailModel;
import microservices.block.models.BlockListModel;
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
        assertTrue(AttributeStandard.isNotDecimal(txnResponse.getTx().getFee()));
        assertTrue(AttributeStandard.isNotDecimal(txnResponse.getTx().getOutSum()));
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
//        assertTrue(AttributeStandard.isNotDecimal(filterTxsRes.getData().stream().map(s->s.getFee()).collect(Collectors.toList())));
        for (int i = 0; i < filterTxsRes.getData().size(); i++) {
            assertTrue(AttributeStandard.isNotDecimal(filterTxsRes.getData().get(i).getFee()));
        }
        assertTrue(AttributeStandard.isNotDecimal(filterTxsRes.getData().stream().map(s->s.getOutSum()).collect(Collectors.toList())));
        assertTrue(AttributeStandard.isNotDecimal(filterTxsRes.getData().stream().map(s->s.getBalance()).collect(Collectors.toList())));
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
        assertTrue(AttributeStandard.areValidHashes(currentTransactionsList.stream().map(s -> s.getHash()).collect(Collectors.toList())));
        assertTrue(AttributeStandard.areValidDates(currentTransactionsList.stream().map(s -> s.getTime()).collect(Collectors.toList()), DATE_FORMAT[0]));
        assertTrue(AttributeStandard.isNotDecimal(currentTransactionsList.stream().map(s->s.getAmount()).collect(Collectors.toList())));
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
//        assertTrue(AttributeStandard.areValidDates(transactionGraphResponseList.stream().map(s -> s.getDate()).collect(Collectors.toList()), DATE_FORMAT[0]));
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
//        assertThat(txnResponse.getSummary())
//                .as("Value of field 'tx.summary' is wrong")
//                .isEqualTo(responseExpected.getSummary());
        assertThat(txnResponse.getContracts())
                .as("Value of field 'tx.contracts' is wrong")
                .isEqualTo(responseExpected.getContracts());
        assertThat(txnResponse.getCollaterals())
                .as("Value of field 'tx.collaterals' is wrong")
                .isEqualTo(responseExpected.getCollaterals());
        return this;
    }
    @Step("Verify response of filter transaction and transaction by hash")
    public TransactionSteps then_verifyTransactionResponseFilterANdHash(FilterTransactionResponse filterTransactionResponseList, TransactionResponse transactionResponseByHash) {
        FilterTransactionResponse.FilterTransactionDetail filterTransactionDetail = filterTransactionResponseList.getData().get(0);

        List<String> addressesInput = new ArrayList<>();
        for (int i =0;i<transactionResponseByHash.getUtxOs().getInputs().size();i++){
            addressesInput.add(transactionResponseByHash.getUtxOs().getInputs().get(i).getAddress());
        }
        Assert.assertTrue(filterTransactionDetail.getAddressesInput().containsAll(addressesInput));

        List<String> addressesOutput = new ArrayList<>();
        for (int i =0;i<transactionResponseByHash.getUtxOs().getOutputs().size();i++){
            addressesOutput.add(transactionResponseByHash.getUtxOs().getOutputs().get(i).getAddress());
        }
        Assert.assertTrue(filterTransactionDetail.getAddressesOutput().containsAll(addressesOutput));

        assertThat(transactionResponseByHash.getTx().getOutSum())
                .as("Value of field 'totalOutput' is wrong")
                .isEqualTo(filterTransactionDetail.getOutSum());
        assertThat(filterTransactionDetail.getHash())
                .as("Value of field 'hash' is wrong")
                .isEqualTo(transactionResponseByHash.getTx().getHash());
        assertThat(filterTransactionDetail.getBlockNo())
                .as("Value of field 'blockNo' is wrong")
                .isEqualTo(transactionResponseByHash.getTx().getBlockNo());
        assertThat(filterTransactionDetail.getBlockHash())
                .as("Value of field 'blockHash' is wrong")
                .isEqualTo(transactionResponseByHash.getTx().getBlockHash());
        assertThat(filterTransactionDetail.getEpochNo())
                .as("Value of field 'epochNo' is wrong")
                .isEqualTo(transactionResponseByHash.getTx().getEpochNo());
        assertThat(filterTransactionDetail.getTime())
                .as("Value of field 'time' is wrong")
                .isEqualTo(transactionResponseByHash.getTx().getTime());
        assertThat(filterTransactionDetail.getFee())
                .as("Value of field 'fee' is wrong")
                .isEqualTo(transactionResponseByHash.getTx().getFee());

        return this;
    }

    @Step("Verify hash of filter transaction and transaction current")
    public TransactionSteps then_verifyTransactionResponseFilterAndCurrent(FilterTransactionResponse filterTransactionResponseList, List<Transaction> transactionCurrentList) {
        List<String> transactionCurrent = new ArrayList<>();
        for (Transaction transaction : transactionCurrentList) {
            transactionCurrent.add(transaction.getHash());
        }
        for(int i=0;i<4;i++){
            Assert.assertTrue(transactionCurrent.contains(filterTransactionResponseList.getData().get(i).getHash()));
        }
        return this;
    }

    @Step("Verify response of block by blockId and transaction by hash")
    public TransactionSteps then_verifyTransactionResponseByHashWithBlock(TransactionResponse transactionResponseByHash, BlockDetailModel blockDetailModel) {

        assertThat(transactionResponseByHash.getTx().getHash())
                .as("Value of field 'hash' is wrong")
                .isEqualTo(blockDetailModel.getHash());
        assertThat(transactionResponseByHash.getTx().getBlockNo())
                .as("Value of field 'blockNo' is wrong")
                .isEqualTo(blockDetailModel.getBlockNo());
        assertThat(transactionResponseByHash.getTx().getBlockHash())
                .as("Value of field 'blockHash' is wrong")
                .isEqualTo(blockDetailModel.getBlockHash());
        assertThat(transactionResponseByHash.getTx().getEpochNo())
                .as("Value of field 'epochNo' is wrong")
                .isEqualTo(blockDetailModel.getEpochNo());
        assertThat(transactionResponseByHash.getTx().getTime())
                .as("Value of field 'time' is wrong")
                .isEqualTo(blockDetailModel.getId());

        List<String> addressesInput = new ArrayList<>();
        for (int i =0;i<transactionResponseByHash.getUtxOs().getInputs().size();i++){
            addressesInput.add(transactionResponseByHash.getUtxOs().getInputs().get(i).getAddress());
        }
        Assert.assertTrue(new HashSet<>(addressesInput).containsAll(blockDetailModel.getAddressesInput()));

        List<String> addressesOutput = new ArrayList<>();
        for (int i =0;i<transactionResponseByHash.getUtxOs().getOutputs().size();i++){
            addressesOutput.add(transactionResponseByHash.getUtxOs().getOutputs().get(i).getAddress());
        }
        Assert.assertTrue(new HashSet<>(addressesOutput).containsAll(blockDetailModel.getAddressesOutput()));

        return this;
    }
}
