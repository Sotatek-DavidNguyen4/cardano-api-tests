package tests;

import base.BaseTest;
import microservices.common.constants.APIErrorCode;
import microservices.common.constants.APIErrorMessage;
import microservices.common.models.ErrorResponse;
import microservices.delegation.models.PoolDetailHeaderModel;
import microservices.txn.models.TransactionResponse;
import microservices.txn.steps.TransactionSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TransactionTests extends BaseTest {
    TransactionSteps txnSteps = new TransactionSteps();
    private TransactionResponse txnResponse;

    @Test(description = "Get the transaction by valid hash", groups = "transactions", dataProvider = "validHash")
    public void get_transaction_by_valid_hash(String hash) {
         txnResponse = (TransactionResponse) txnSteps.when_getTransactionByHash(hash)
                .validateStatusCode(200)
                .saveResponseObject(TransactionResponse.class);
         txnSteps.then_verifyTransactionResponse(txnResponse, hash);
    }

    @Test(description = "Get the transaction by invalid hash", groups = "transactions", dataProvider = "invalidHash")
    public void get_transaction_by_invalid_hash(String hash) {
        txnSteps.when_getTransactionByHash(hash)
                .then_verifyErrorResponse(400, APIErrorMessage.TRANSACTION_NOT_FOUND, APIErrorCode.TRANSACTION_NOT_FOUND);
    }

    @DataProvider(name ="validHash")
    public Object[][] dataValidHash() {
        return new Object[][]{
                {"1b82c66a192068f3ab6bab8676ae3001931aa4e9346c274ade08cb98523f52f6"},
                {"b731574b44de062ade1e70d0040abde47a6626c7d8e98816a9d87e6bd6228b45"},
                {"5526b1373acfc774794a62122f95583ff17febb2ca8a0fe948d097e29cf99099"}
        };
    }
    @DataProvider(name ="invalidHash")
    public Object[][] dataInvalidHash() {
        return new Object[][]{
                {"ab008b3844d8ef2dc63451491a35a247ede5669fcf0a0559adc712f74bfebe29"},
                {"f8374de85bc4777f7dee56dea498e87f4151f6a8e534ddac83b29b8199a1b67f"},
                {"@#$%"},
                {"   "},
                {"asset1c6t4elexwkpuzq08ssylhhmc78ahlz0sgw5a7y"},
                {"asset1c0vymmx0nysjaa8q5vah78jmuqyew3kjm48azr"}
        };
    }
}
