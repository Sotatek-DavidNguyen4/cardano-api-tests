package tests.stakes;

import base.BaseTest;
import microservices.stakeKey.models.listAddress.StakeListAddressDataModel;
import microservices.stakeKey.models.listAddress.StakeListAddressModel;
import microservices.stakeKey.steps.StakeKeySteps;
import org.apache.commons.collections.MultiMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateMultiParameters;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StakeKeyListAddressTests extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    private String stakeKey = "stake1u9lgu9jxyst64axcyfyj9qzsadntz6s58szr8vydu72xwhcz7ycmd";
    private long nubmerPage;
    @Test(description = "get stake list address", groups = {"stake", "stake_list_address"}, priority = 0)
    public void getStakeListAddress(){
        String pathStakeListAddressSchema = "schemaJson/stakes/stakeListAddress.json";
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        StakeListAddressModel stakeListAddressModel = (StakeListAddressModel)
                stakeKeySteps.getStakeListAddress(param, stakeKey)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(StakeListAddressModel.class);

        nubmerPage = stakeListAddressModel.getTotalItems()/15 + 1;
        ArrayList<Object> elements = new ArrayList<>();
        for(StakeListAddressDataModel stakeListAddressDataModel : stakeListAddressModel.getData()){
            elements.add(stakeListAddressDataModel.getBalance());
        }

        stakeKeySteps.then_verifyPageStakeListAddressResponse(stakeListAddressModel, param)
                .then_verifySizeStakeListAddressResponse(stakeListAddressModel, param, 15)
                .verifyElementsIsNotDecimal(elements)
                .validateResponseSchema(pathStakeListAddressSchema);
    }
    @Test(description = "get stake list addres | stakeKey invalid", groups = {"stake", "stake_list_address"}, dataProvider = "stake")
    public void getStakeListAddressStakeKeyInvalid(String stakeKey){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        StakeListAddressModel stakeListAddressModel = (StakeListAddressModel)
                stakeKeySteps.getStakeListAddress( param, stakeKey)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(StakeListAddressModel.class);
        stakeKeySteps.then_verifyPageStakeListAddressResponse(stakeListAddressModel, param)
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
        stakeKeySteps.then_verifyPageStakeListAddressResponse(stakeListAddressModel, param);
    }
    @DataProvider(name = "page")
    public Object[][] DatasetPage(){
        return new Object[][]{
                {"1"},
                {"abc"},
                {"-10"},
                {"@#$"},
                {" "},
        };
    }
    @Test(description = "get stake list addres with page = totalPage + 1", groups = {"stake", "stake_list_address"}, priority = 1)
    public void getStakeListAddressWithPage2(){
        MultiMap param = new CreateMultiParameters()
                .withPage(""+nubmerPage+"")
                .getParamsMap();
        StakeListAddressModel stakeListAddressModel = (StakeListAddressModel)
                stakeKeySteps.getStakeListAddress(param, stakeKey)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(StakeListAddressModel.class);
        stakeKeySteps.then_verifyPageStakeListAddressResponse(stakeListAddressModel, param)
                .then_verifySizeStakeListAddressResponse(stakeListAddressModel, param, 0);
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
        stakeKeySteps.then_verifySizeStakeListAddressResponse(stakeListAddressModel, param, 15);
    }
    @DataProvider(name = "size")
    public Object[][] DatasetSize(){
        return new Object[][]{
                {"5"},
                {"abc"},
                {"-10"},
                {"@#$"},
                {" "},
        };
    }
}
