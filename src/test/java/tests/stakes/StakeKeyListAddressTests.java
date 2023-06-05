package tests.stakes;

import base.BaseTest;
import microservices.stakeKey.models.listAddress.StakeListAddressModel;
import microservices.stakeKey.steps.StakeKeySteps;
import org.apache.commons.collections.MultiMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateMultiParameters;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class StakeKeyListAddressTests extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    private String stakeKey = "stake_test1ur8u69tv9k3fnc7q8a3xqw6ugs5t4nq4dmjy02zw3w0zjgg7tewuu";
    @Test(description = "get stake list address", groups = {"stake", "stake_list_address"})
    public void getStakeListAddress(){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        StakeListAddressModel stakeListAddressModel = (StakeListAddressModel)
                stakeKeySteps.getStakeListAddress(param, stakeKey)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(StakeListAddressModel.class);
        stakeKeySteps.then_verifyPageStakeListAddressResponse(stakeListAddressModel, param, 20)
                .then_verifySizeStakeListAddressResponse(stakeListAddressModel, param, 20);
    }
    @Test(description = "get stake list address | all key", groups = {"stake", "stake_list_address"})
    public void getStakeListAddressAllKey(){
        MultiMap param = new CreateMultiParameters()
                .withPage("0")
                .withPageSize("20")
                .getParamsMap();
        StakeListAddressModel stakeListAddressModel = (StakeListAddressModel)
                stakeKeySteps.getStakeListAddress(param, stakeKey)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(StakeListAddressModel.class);
        stakeKeySteps.then_verifyPageStakeListAddressResponse(stakeListAddressModel, param, 20)
                .then_verifySizeStakeListAddressResponse(stakeListAddressModel, param, 20);
    }
    @Test(description = "get stake list addres | stakeKey invalid", groups = {"stake", "stake_list_address"}, dataProvider = "stake")
    public void getStakeListAddressStakeKeyInvalid(String stakeKey){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        StakeListAddressModel stakeListAddressModel = (StakeListAddressModel)
                stakeKeySteps.getStakeListAddress( param, stakeKey)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(StakeListAddressModel.class);
        stakeKeySteps.then_verifyPageStakeListAddressResponse(stakeListAddressModel, param, 0)
                .then_verifySizeStakeListAddressResponse(stakeListAddressModel, param, 0);
    }
    @DataProvider(name = "stake")
    public Object[][] DatasetStakeyInvalid(){
        return new Object[][]{
                {"@#$"},
                {" "},
                {"abcd"},
                {"12345"}
        };
    }
    @Test(description = "get stake list addres with page", groups = {"stake", "stake_list_address"}, dataProvider = "page")
    public void getStakeListAddressWithPage(String page){
        MultiMap param = new CreateMultiParameters()
                .withPage(page)
                .getParamsMap();
        StakeListAddressModel stakeListAddressModel = (StakeListAddressModel)
                stakeKeySteps.getStakeListAddress(param, stakeKey)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(StakeListAddressModel.class);
        stakeKeySteps.then_verifyPageStakeListAddressResponse(stakeListAddressModel, param, 20);
    }
    @DataProvider(name = "page")
    public Object[][] DatasetPage(){
        return new Object[][]{
                {"100"},
                {"abc"},
                {"-10"},
                {"@#$"},
                {" "},
        };
    }
    @Test(description = "get stake list addres with size", groups = {"stake", "stake_list_address"}, dataProvider = "size")
    public void getStakeListAddressWithSize(String size){
        MultiMap param = new CreateMultiParameters()
                .withPageSize(size)
                .getParamsMap();
        StakeListAddressModel stakeListAddressModel = (StakeListAddressModel)
                stakeKeySteps.getStakeListAddress(param, stakeKey)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(StakeListAddressModel.class);
        stakeKeySteps.then_verifySizeStakeListAddressResponse(stakeListAddressModel, param, 20);
    }
    @DataProvider(name = "size")
    public Object[][] DatasetSize(){
        return new Object[][]{
                {"100"},
                {"abc"},
                {"-10"},
                {"@#$"},
                {" "},
        };
    }
}
