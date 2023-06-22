package tests.stakes;

import base.BaseTest;
import microservices.stakeKey.models.instantaneousReward.InstantaneousRewardDataModel;
import microservices.stakeKey.models.instantaneousReward.InstantaneousRewardModel;
import microservices.stakeKey.steps.StakeKeySteps;
import org.apache.commons.collections.MultiMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateMultiParameters;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Map;

public class StakeKeyInstantaneousRewards extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    private String stakeKey = "stake1uypy44wqjznc5w9ns9gsguz4ta83jekrg9d0wupa7j3zsacwvq5ex";
    private long numberPage;
    @Test(description = "get stake instantaneous rewards with stakeKey", groups = {"stake", "stake_instantaneour_rewards"}, priority = 0)
    public void getStakeInstantaneousReward(){
        String pathStakeInstantaneousRewardsSchema = "schemaJson/stakes/stakeInstantaneousRewards.json";
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        InstantaneousRewardModel instantaneousRewardModel = (InstantaneousRewardModel)
                stakeKeySteps.getStakeInstantaneousRewards(stakeKey)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(InstantaneousRewardModel.class);
        numberPage = instantaneousRewardModel.getTotalItems()/6 +1;
        ArrayList<Object> elements = new ArrayList<>();
        for(InstantaneousRewardDataModel instantaneousRewardDataModel : instantaneousRewardModel.getData()){
            elements.add(instantaneousRewardDataModel.getAmount());
        }

        stakeKeySteps.then_verifyPageInstantaneousResponse(instantaneousRewardModel, param)
                .then_verifySizeInstantaneousResponse(instantaneousRewardModel, param, 6)
                .verifyStakeInstantaneousRewards(instantaneousRewardModel.getData())
                .verifyElementsIsNotDecimal(elements)
                .validateResponseSchema(pathStakeInstantaneousRewardsSchema);
    }
    @Test(description = "get stake instantaneous rewards with stakeKey | invalid", groups = {"stake", "stake_instantaneour_rewards"}, dataProvider = "stakeKey")
    public void getStakeInstantaneousRewardWithStakeKeyInvalid(Object stakeKey){
        Map<String, Object> param = new CreateParameters()
                .getParamsMap();
        InstantaneousRewardModel instantaneousRewardModel = (InstantaneousRewardModel)
        stakeKeySteps.getStakeInstantaneousRewards(stakeKey)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(InstantaneousRewardModel.class);
        stakeKeySteps.then_verifyPageInstantaneousResponse(instantaneousRewardModel, param)
                .then_verifySizeInstantaneousResponse(instantaneousRewardModel, param, 0);
    }
    @DataProvider(name = "stakeKey")
    public Object[][] DatasetStakeKey(){
        return new Object[][]{
                {"@#$"},
                {"  "},
                {"abcd"},
                {"12345"}
        };
    }
    @Test(description = "get stake instantaneous rewards with page", groups = {"stake", "stake_instantaneour_rewards"}, dataProvider = "page")
    public void getStakkeInstantaneousRewardPage(String page){
        MultiMap param = new CreateMultiParameters()
                .withPage(page)
                .getParamsMap();
        InstantaneousRewardModel instantaneousRewardModel = (InstantaneousRewardModel)
        stakeKeySteps.getStakeInstantaneousRewardParam(stakeKey, param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(InstantaneousRewardModel.class);
        stakeKeySteps.then_verifyPageInstantaneousResponse(instantaneousRewardModel, param);
    }
    @DataProvider(name = "page")
    public Object[][] DatasetPage(){
        return new Object[][]{
//                {"1"},
                {"abc"},
                {"-10"},
                {" "},
                {"@#$%"},
        };
    }
//    @Test(description = "get stake instantaneous rewards with page = totalPage + 1", groups = {"stake", "stake_instantaneour_rewards"}, priority = 1)
//    public void getStakkeInstantaneousRewardParam2(){
//        MultiMap param = new CreateMultiParameters()
//                .withPage(""+numberPage+"")
//                .getParamsMap();
//        InstantaneousRewardModel instantaneousRewardModel = (InstantaneousRewardModel)
//                stakeKeySteps.getStakeInstantaneousRewardParam(stakeKey, param)
//                        .validateStatusCode(HttpURLConnection.HTTP_OK)
//                        .saveResponseObject(InstantaneousRewardModel.class);
//        stakeKeySteps.then_verifyPageInstantaneousResponse(instantaneousRewardModel, param)
//                .then_verifySizeInstantaneousResponse(instantaneousRewardModel, param, 0);
//    }
    @Test(description = "get stake instantaneous rewards with size", groups = {"stake", "stake_instantaneour_rewards"}, dataProvider = "size")
    public void getStakkeInstantaneousRewardSize(String size){
        MultiMap param = new CreateMultiParameters()
                .withPageSize(size)
                .getParamsMap();
        InstantaneousRewardModel instantaneousRewardModel = (InstantaneousRewardModel)
                stakeKeySteps.getStakeInstantaneousRewardParam(stakeKey, param)
                        .validateStatusCode(HttpURLConnection.HTTP_OK)
                        .saveResponseObject(InstantaneousRewardModel.class);
        stakeKeySteps.then_verifySizeInstantaneousResponse(instantaneousRewardModel, param, 6);
    }
    @DataProvider(name = "size")
    public Object[][] DatasetSize(){
        return new Object[][]{
                {"5"},
                {"abc"},
                {"-10"},
                {" "},
                {"!@@$$"},
        };
    }
}
