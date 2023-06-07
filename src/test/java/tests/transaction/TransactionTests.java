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
    private String type;

    @Test(description = "Get the transaction by valid hash", groups = "transactions", dataProvider = "validHash")
    public void get_transaction_by_valid_hash(String hash) {
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
        params.put("page", "1");
        params.put("size", "4");
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

    @Test(description = "Get the transaction by valid hash", groups = "transactions")
    public void get_transaction_by_hash() {
        JsonObject map = null;
        map = JsonUtils.readJsonFile("preProd/pre_prod_api_data.json");
        FIRST_TRANSACTION = (TransactionInfo) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("first_transaction").toString(), TransactionInfo.class);
        System.out.println(FIRST_TRANSACTION.getBlockHash());
    }

    @DataProvider(name ="responseWithDataHash")
    public Object[][] dataHash() {
        return new Object[][]{
                {FIRST_TRANSACTION},
                new TransactionInfo[]{FIRST_TRANSACTION},
//                new TransactionInfo[]{RANDOM_TRANSACTION},
//                new TransactionInfo[]{TRANSACTION_HAVE_30000000000_ADA},
//                new TransactionInfo[]{TRANSACTION_HAVE_29999998493561943_ADA},
//                new TransactionInfo[]{TRANSACTION_BYRON_ERA},
//                new TransactionInfo[]{TRANSACTION_SHELLY_ERA},
//                new TransactionInfo[]{TRANSACTION_ALLEGRA_ERA},
//                new TransactionInfo[]{TRANSACTION_MARY_ERA},
//                new TransactionInfo[]{TRANSACTION_ALOZO_ERA},
//                new TransactionInfo[]{TRANSACTION_BABBAGE_ERA}
        };
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
                {"1", ""},
                {"a", ""},
                {"-2", ""},
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


}
