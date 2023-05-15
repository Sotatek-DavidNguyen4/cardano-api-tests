package microservices.txn.steps;

import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;
import microservices.common.steps.BaseSteps;
import microservices.txn.models.TransactionResponse;

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
            assertThat(txnResponse.getTx().getTotalOutput())
                    .as("Value of field 'tx.totalOutput' is wrong")
                    .isEqualTo("30000000000000000");
        }
        return this;
    }
}
