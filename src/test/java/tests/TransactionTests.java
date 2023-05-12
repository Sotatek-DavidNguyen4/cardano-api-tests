package tests;

import microservices.delegation.models.PoolDetailHeaderModel;
import microservices.txn.models.TransactionResponse;
import microservices.txn.steps.TransactionSteps;
import org.testng.annotations.Test;

public class TransactionTests {
    TransactionSteps txnSteps = new TransactionSteps();
    private TransactionResponse txnResponse;
    private String hash, totalOutput;

    @Test(description = "Get transaction by hash", groups = "transactions")
    public void get_transaction_by_valid_hash() {
        hash = "1b82c66a192068f3ab6bab8676ae3001931aa4e9346c274ade08cb98523f52f6";
         txnResponse = (TransactionResponse) txnSteps.when_getTransactionByHash(hash)
                .validateStatusCode(200)
                .saveResponseObject(TransactionResponse.class);
         txnSteps.then_verifyTransactionResponse(txnResponse, hash, totalOutput);
    }
}
