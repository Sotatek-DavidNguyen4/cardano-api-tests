package microservices.txn.steps;

import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;
import microservices.txn.models.TransactionResponse;

public class TransactionSteps extends BaseApi {

    @Step("Get transaction by hash")
    public TransactionSteps when_getTransactionByHash(String hash) {
        sendGet(Endpoints.TRANSACTION_HASH, Endpoints.HASH_ID, hash);
        return this;
    }

    @Step("Verify transaction response")
    public TransactionSteps then_verifyTransactionResponse(TransactionResponse txnResponse, String hash, String totalOutput) {
//        assertThat(txnResponse.getTx().getHash())
//                .as("Value of field 'tx.hash' is wrong")
//                .isEqualTo(hash);
//        assertThat(txnResponse.getTx().getTotalOutput())
//                .as("Value of field 'tx.totalOutput' is wrong")
//                .isEqualTo(totalOutput);
        return this;
    }
}
