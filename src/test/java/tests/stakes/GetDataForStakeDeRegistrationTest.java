package tests.stakes;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import microservices.stakeKey.models.deRegistration.StakeDeRegistration;
import microservices.stakeKey.steps.StakeKeySteps;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.net.HttpURLConnection;



@Epic("cardano")
@Feature("api-stake-key-controller")
@Story("Get Data For Stake De Registration")
public class GetDataForStakeDeRegistrationTest extends BaseTest {
    StakeKeySteps stakeKeySteps = new StakeKeySteps();
    StakeDeRegistration stakeDeRegistration ;
    @Test(description = "Verify data for stake de registration",groups = "stake-key-controller",dataProvider = "getParamForStakeDeRegistration")
    public void getDataForStakeDeRegistration(Object page,Object size){
        MultiMap params = new MultiValueMap();
        params.put("page", page);
        params.put("size", size);
        stakeDeRegistration = (StakeDeRegistration) stakeKeySteps.getDataForStakeDeRegistration(params)
                                                .validateResponse(HttpURLConnection.HTTP_OK)
                                                .saveResponseObject(StakeDeRegistration.class);

        stakeKeySteps.then_verifyStakeDeRegistrationResponse(stakeDeRegistration,params)
                     .then_verifyFormatOfStakeDeRegistrationResponse(stakeDeRegistration.getData())
                     .then_verifyStakeDeRegistrationResponseNotNull(stakeDeRegistration.getData());
    }
    @DataProvider(name = "getParamForStakeDeRegistration")
    public Object[][] getParamForStakeDeRegistration(){
        return new Object[][]{
                {"",""},
                {"0","20"},
                {"10",""},
                {"abc",""},
                {"-10",""},
                {" ",""},
                {"@#$%%",""},
                {"","1"},
                {"","abc"},
                {"","-10"},
                {""," "},
                {"","@#$%%"},
        };
    }
}
