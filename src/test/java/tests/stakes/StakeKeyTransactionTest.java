package tests.stakes;

import base.BaseTest;
import microservices.stakeKey.models.instantaneousReward.InstantaneousRewardDataModel;
import microservices.stakeKey.models.instantaneousReward.InstantaneousRewardModel;
import microservices.stakeKey.models.registration.StakeRegistration;
import microservices.stakeKey.models.transaction.StakeTransaction;
import microservices.stakeKey.steps.StakeKeySteps;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateMultiParameters;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Map;

public class StakeKeyTransactionTest extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    private String stakeKey = "stake178uvw34lh566mkv2kqrech64vssxqqrux65z3fsl6txujqcr24yys";

    StakeTransaction stakeTransaction;
    @Test(description = "get stake transaction stakeKey", groups = {"stake-key-controller", "stake_transaction"}, priority = 0)
    public void getStakeTransaction(){
        String pathSchema = "schemaJson/stakes/stakeTransaction.json";
        MultiMap params = new MultiValueMap();
        params.put("page", null);
        params.put("size", null);
        stakeTransaction = (StakeTransaction)
                stakeKeySteps.getStakeTransaction(stakeKey,params)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(StakeTransaction.class);

        stakeKeySteps.then_verifyStakeTransactionResponse(stakeTransaction,params)
                .verifyStakeTransactionFormat(stakeTransaction.getData())
                .verifyElementsIsNotDecimalStakeTransaction(stakeTransaction.getData())
                .validateResponseSchema(pathSchema);
    }
    @Test(description = "get stake transaction stakeKey with param", groups = {"stake-key-controller", "stake_transaction"}, dataProvider = "stakeKey")
    public void getStakeTransactionWithParam(String page,String size){
        MultiMap params = new MultiValueMap();
        params.put("page", page);
        params.put("size", size);

        stakeTransaction = (StakeTransaction)
                stakeKeySteps.getStakeTransaction(stakeKey,params)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(StakeTransaction.class);

        stakeKeySteps.then_verifyStakeTransactionResponse(stakeTransaction,params)
                .verifyStakeTransactionFormat(stakeTransaction.getData())
                .verifyElementsIsNotDecimalStakeTransaction(stakeTransaction.getData());

    }
    @DataProvider(name = "stakeKey")
    public Object[][] DatasetStakeKey(){
        return new Object[][]{
                {"10",""},
                {"abc",""},
                {"-10",""},
                {" ",""},
                {"@#$",""},
                {"","10"},
                {"","abc"},
                {"","-10"},
                {""," "},
                {"","!@@$$"},

        };
    }

    @Test(description = "Verify data for stake transaction with total page",groups = "stake-key-controller")
    public void getDataForStakeRegistrationWithTotalPage(){
        MultiMap params1 = new MultiValueMap();
        params1.put("page", null);
        params1.put("size", null);
        stakeTransaction = (StakeTransaction) stakeKeySteps.getDataForStakeRegistration(params1)
                .saveResponseObject(StakeTransaction.class);

        MultiMap params = new MultiValueMap();
        params.put("page", String.valueOf(stakeTransaction.getTotalPages()+1));

        stakeTransaction = (StakeTransaction) stakeKeySteps.getStakeTransaction(stakeKey,params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(StakeTransaction.class);

        stakeKeySteps.then_verifyStakeTransactionResponseWithTotalPage(stakeTransaction,params);

    }

}
