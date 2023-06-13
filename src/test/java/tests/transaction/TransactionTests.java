package tests.transaction;

import base.BaseTest;
import com.google.gson.JsonObject;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.common.constants.APIErrorCode;
import microservices.common.constants.APIErrorMessage;
import microservices.txn.models.*;
import microservices.txn.steps.TransactionSteps;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.JsonUtils;
import util.ObjectMappingUtils;

import java.net.HttpURLConnection;
import java.util.List;
import static data.ApiResponseData.*;

@Epic("cardano")
@Feature("api-transactions")
public class TransactionTests extends BaseTest {
    TransactionSteps txnSteps = new TransactionSteps();
    private TransactionResponse txnResponse;
    private String type, hash;

    @Test(description = "Get the transaction by valid hash", groups = "transactions")
    public void get_transaction_by_valid_hash() {
         hash = "d5d9dfcb3e4237cd89274bef99b6cbbfd7e635fd2b11958b9a531f85d5551532";
         txnResponse = (TransactionResponse) txnSteps.when_getTransactionByHash(hash)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TransactionResponse.class);
         txnSteps.then_verifyTransactionResponse(txnResponse, hash);
    }

    @Test(description = "Get the transaction by invalid hash", groups = "transactions", dataProvider = "invalidHash")
    public void get_transaction_by_invalid_hash(String hash) {
        txnSteps.when_getTransactionByHash(hash)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, APIErrorMessage.TRANSACTION_NOT_FOUND, APIErrorCode.TRANSACTION_NOT_FOUND);
    }

    @Test(description = "Get filter transaction without sort", groups = "transactions", dataProvider = "paramWithPage&Size")
    public void get_filter_transaction_success_without_sort(String page, String size) {
        MultiMap params = new MultiValueMap();
        params.put("page", page);
        params.put("size", size);
        FilterTransactionResponse filterTxsRes = (FilterTransactionResponse) txnSteps.when_filterTransaction(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(FilterTransactionResponse.class);
        txnSteps.then_verifyFilterTransactionResponse(filterTxsRes, params);
    }

    @Test(description = "Get filter transaction with sort", groups = "transactions")
    public void get_filter_transaction_success_with_sort() {
        MultiMap params = new MultiValueMap();
        params.put("sort", "fee,ASC");
        FilterTransactionResponse filterTxsRes = (FilterTransactionResponse) txnSteps.when_filterTransaction(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(FilterTransactionResponse.class);
        txnSteps.then_verifyFilterTransactionResponse(filterTxsRes, params);

        params = new MultiValueMap();
        params.put("sort", "fee,DESC");
        filterTxsRes = (FilterTransactionResponse) txnSteps.when_filterTransaction(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(FilterTransactionResponse.class);
        txnSteps.then_verifyFilterTransactionResponse(filterTxsRes, params);

        params = new MultiValueMap();
        params.put("sort", "outSum,ASC");
        filterTxsRes = (FilterTransactionResponse) txnSteps.when_filterTransaction(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(FilterTransactionResponse.class);
        txnSteps.then_verifyFilterTransactionResponse(filterTxsRes, params);

        params = new MultiValueMap();
        params.put("sort", "outSum,DESC");
        filterTxsRes = (FilterTransactionResponse) txnSteps.when_filterTransaction(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(FilterTransactionResponse.class);
        txnSteps.then_verifyFilterTransactionResponse(filterTxsRes, params);

        params = new MultiValueMap();
        params.put("sort", "fee,DESC");
        params.put("sort", "outSum,DESC");
        params.put("page", "0");
        params.put("size", "20");
        filterTxsRes = (FilterTransactionResponse) txnSteps.when_filterTransaction(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(FilterTransactionResponse.class);
        txnSteps.then_verifyFilterTransactionResponse(filterTxsRes, params);
    }

    @Test(description = "Get filter transaction with sort", groups = "transactions", dataProvider = "invalidSort")
    public void get_filter_transaction_unsuccess_with_sort(String sort) {
        MultiMap params = new MultiValueMap();
        params.put("sort", sort);
        txnSteps.when_filterTransaction(params)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_INTERNAL_ERROR, APIErrorMessage.UNKNOWN_MESSAGE, APIErrorCode.UNKNOWN_CODE);
    }

    @Test(description = "Get number transaction with invalid days", groups = "transactions", dataProvider = "invalidType")
    public void get_number_transaction_with_invalid_days(String type) {
        txnSteps.when_getTransactionOnFixableDays(type)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_INTERNAL_ERROR, APIErrorMessage.UNKNOWN_MESSAGE, APIErrorCode.UNKNOWN_CODE);
    }

    @Test(description = "Get number transaction on fixable days", groups = "transactions", enabled = false)
    public void get_number_transaction_on_fixable_days() {
        //type = ONE_DAY
        type = "ONE_DAY";
        List<TransactionGraphResponse> transactionGraphResponseList = txnSteps.when_getTransactionOnFixableDays(type)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseListObject(TransactionGraphResponse[].class);
        txnSteps.then_verifyTypeTransactionResponse(transactionGraphResponseList,1);

       //type = ONE_WEEK
        type = "ONE_WEEK";
        transactionGraphResponseList = txnSteps.when_getTransactionOnFixableDays(type)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseListObject(TransactionGraphResponse[].class);
        txnSteps.then_verifyTypeTransactionResponse(transactionGraphResponseList,7);

        //type = TWO_WEEK
        type = "TWO_WEEK";
        transactionGraphResponseList = txnSteps.when_getTransactionOnFixableDays(type)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseListObject(TransactionGraphResponse[].class);
        txnSteps.then_verifyTypeTransactionResponse(transactionGraphResponseList,14);

    }

    @Test(description = "Get current transaction", groups = "transactions")
    public void get_current_transaction() {
        List<Transaction> currentTransactionsList = txnSteps.when_getCurrentTransaction()
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseListObject(Transaction[].class);
        txnSteps.then_verifyCurrentTransactionResponse(currentTransactionsList);
    }

    @Test(description = "Get the transaction by valid hash", groups = "transactions", dataProvider = "responseWithDataHashInPreProd")
    public void get_transaction_by_hash(TransactionResponse expectedResponse) {
        // This data test is not in preprod yet
        if (System.getProperty("cardanoAPI.baseEnv").contains("preprod")) {
            txnResponse = (TransactionResponse) txnSteps.when_getTransactionByHash(expectedResponse.getTx().getHash())
                    .validateStatusCode(HttpURLConnection.HTTP_OK)
                    .saveResponseObject(TransactionResponse.class);
            txnSteps.then_verifyTransactionResponseWithDataTest(txnResponse, expectedResponse);

        }
    }

    @Test(description = "Get the transaction by valid hash in mainnet", groups = "transactions", dataProvider = "responseWithDataHashInMainnet")
    public void get_transaction_by_hash_in_mainnet(TransactionResponse expectedResponse) {
        // This data test is not in mainnet yet
        if (System.getProperty("cardanoAPI.baseEnv").contains("mainnet")) {
            txnResponse = (TransactionResponse) txnSteps.when_getTransactionByHash(expectedResponse.getTx().getHash())
                    .validateStatusCode(HttpURLConnection.HTTP_OK)
                    .saveResponseObject(TransactionResponse.class);
            System.out.println(txnResponse);
            System.out.println(expectedResponse);
            txnSteps.then_verifyTransactionResponseWithDataTest(txnResponse, expectedResponse);

        }
    }

    @DataProvider(name ="invalidHash")
    public Object[][] dataInvalidHash() {
        return new Object[][]{
                {"0bd8c4931f4f2fbe89ba5d6f9bfe429c39176133bfbfcfef87a098c1b3abcvfl"},
                {"dc84784e750e7395d31ee51f3e640692203356d31205ed1122da9a655bdd972c"},
                {"@#$"},
                {"   "},
                {"asset1c6t4elexwkpuzq08ssylhhmc78ahlz0sgw5a7y"},
                {"asset1c0vymmx0nysjaa8q5vah78jmuqyew3kjm48azr"}
        };
    }
    @DataProvider(name ="paramWithPage&Size")
    public Object[][] dataParamPageAndSize() {
        return new Object[][]{
                // size is null
                {"",""},
                {"10", ""},
                {"a", ""},
                {"-10", ""},
                {"  ", ""},
                {"@#$", ""},
                // page is null
                {"", "1"},
                {"", "a"},
                {"", "-2"},
                {"", "  "},
                {"", "@#$"},
        };
    }
    @DataProvider(name ="invalidSort")
    public Object[][] dataInvalidSort() {
        return new Object[][]{
                {"a"},
                {"-2"},
                {"@#$"},
        };
    }

    @DataProvider(name ="invalidType")
    public Object[][] dataInvalidType() {
        return new Object[][]{
                {"TWO_DAY"},
                {"THREE_WEEK"},
                {"TWO_MONTH"},
                {"FOUR_MONTH"},
                {"ONEDAY"},
                {"ONE DAY"},
                {"one day"},
                {"1 day"},
                {"   "},
        };
    }

    @DataProvider(name ="responseWithDataHashInPreProd")
    public Object[][] dataHashInPreProd() {
        return new Object[][]{
                {FIRST_TRANSACTION},
                {RANDOM_TRANSACTION},
//                {TRANSACTION_HAVE_30000000000_ADA},
//                {TRANSACTION_HAVE_29999998493561943_ADA}
        };
    }

    @DataProvider(name ="responseWithDataHashInMainnet")
    public Object[][] dataHashInMainnet() {
        return new Object[][]{
                {TRANSACTION_BYRON_ERA},
                {TRANSACTION_SHELLY_ERA},
                {TRANSACTION_BABBAGE_ERA},
                {TRANSACTION_ALOZO_ERA},
                {TRANSACTION_MARY_ERA},
                {TRANSACTION_ALLEGRA_ERA}
        };
    }

}
