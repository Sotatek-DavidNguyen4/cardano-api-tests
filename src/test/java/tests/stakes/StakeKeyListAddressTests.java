package tests.stakes;

import base.BaseTest;
import microservices.stakeKey.models.StakeHistory;
import microservices.stakeKey.models.listAddress.StakeListAddressModel;
import microservices.stakeKey.steps.StakeKeySteps;
import org.apache.commons.collections.MultiMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateMultiParameters;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

public class StakeKeyListAddressTests extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    private String stakeKey = "stake_test1ur8u69tv9k3fnc7q8a3xqw6ugs5t4nq4dmjy02zw3w0zjgg7tewuu";
    @Test(description = "get stake list address", groups = {"stake", "stake_list_address"})
    public void getStakeListAddress(){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        StakeListAddressModel stakeListAddressModel = (StakeListAddressModel)
                stakeKeySteps.getListAddress(param, stakeKey)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(StakeListAddressModel.class);
        stakeKeySteps.then_verifyFilterStakeListAddressResponse(stakeListAddressModel, param);
    }
    @Test(description = "get stake list address | all key", groups = {"stake", "stake_list_address"})
    public void getStakeListAddressAllKey(){
        MultiMap param = new CreateMultiParameters()
                .withPage("0")
                .withPageSize("20")
                .getParamsMap();
        StakeListAddressModel stakeListAddressModel = (StakeListAddressModel)
                stakeKeySteps.getListAddress(param, stakeKey)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(StakeListAddressModel.class);
        stakeKeySteps.then_verifyFilterStakeListAddressResponse(stakeListAddressModel, param);
    }
//    @Test(description = "get stake list address | stakeKey invalid", groups = {"stake", "stake_list_address"}, dataProvider = "stakeKey")
//    public void getStakeListAddressStakeKeyInvalid(String stake){
//        MultiMap param = new CreateMultiParameters()
//                .getParamsMap();
//        StakeListAddressModel stakeListAddressModel = (StakeListAddressModel)
//                stakeKeySteps.getListAddress(param, stake)
//                        .validateStatusCode(HttpURLConnection.HTTP_OK)
//                        .saveResponseObject(StakeListAddressModel.class);
//        stakeKeySteps.then_verifyFilterStakeListAddressResponse(stakeListAddressModel, param);
//    }
//    @DataProvider(name = "stakeKey")
//    public Object[][] DatasetStake(){
//        return new Object[][]{
////                {"@#$"},
//                {"  "},
//                {""},
//                {"abc"},
//                {"12345"}
//        };
//    }
}
