package microservices.txn.steps;

import com.google.common.collect.Ordering;
import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;
import microservices.common.constants.RequestParams;
import microservices.common.steps.BaseSteps;
//import microservices.txn.constants.Transaction;
import microservices.common.util.SortListUtil;
import microservices.txn.models.FilterTransactionResponse;
import microservices.txn.models.TransactionInfo;
import microservices.txn.models.TransactionResponse;
import org.assertj.core.api.Assertions;
import org.testng.Assert;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        }


        return this;
    }
}
