package tests.stakes;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import microservices.stakeKey.models.deRegistration.StakeDeRegistration;
import microservices.stakeKey.models.registration.StakeRegistration;
import microservices.stakeKey.steps.StakeKeySteps;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.net.HttpURLConnection;

import static constants.AttributeFormats.STATKE_ADDRESS_LENGTH;
import static constants.Environment.isPreProd;


@Epic("cardano")
@Feature("api-stake-key-controller")
@Story("Get Data For Stake De Registration")
public class GetDataForStakeDeRegistrationTest extends BaseTest {
    StakeKeySteps stakeKeySteps = new StakeKeySteps();
    StakeDeRegistration stakeDeRegistration ;
    @Test(description = "Verify data for stake de registration",groups = "stake-key-controller",dataProvider = "getParamForStakeDeRegistration")
    public void getDataForStakeDeRegistration(Object page,Object size){
        int length = isPreProd() ? STATKE_ADDRESS_LENGTH[0] : STATKE_ADDRESS_LENGTH[1];
        MultiMap params = new MultiValueMap();
        params.put("page", page);
        params.put("size", size);
        stakeDeRegistration = (StakeDeRegistration) stakeKeySteps.getDataForStakeDeRegistration(params)
                                                .validateResponse(HttpURLConnection.HTTP_OK)
                                                .saveResponseObject(StakeDeRegistration.class);

        stakeKeySteps.then_verifyStakeDeRegistrationResponse(stakeDeRegistration,params)
                     .then_verifyFormatOfStakeDeRegistrationResponse(stakeDeRegistration.getData(),length)
                     .then_verifyStakeDeRegistrationResponseNotNull(stakeDeRegistration.getData());
    }
    @DataProvider(name = "getParamForStakeDeRegistration")
    public Object[][] getParamForStakeDeRegistration(){
        return new Object[][]{
                {"",""},
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

    @Test(description = "Verify data for stake de registration with total page",groups = "stake-key-controller")
    public void getDataForStakeDeRegistrationWithTotalPage(){
        MultiMap params1 = new MultiValueMap();
        params1.put("page", null);
        params1.put("size", null);
        stakeDeRegistration = (StakeDeRegistration) stakeKeySteps.getDataForStakeRegistration(params1)
                .saveResponseObject(StakeDeRegistration.class);

        MultiMap params = new MultiValueMap();
        params.put("page", String.valueOf(stakeDeRegistration.getTotalPages()+1));

        stakeDeRegistration = (StakeDeRegistration) stakeKeySteps.getDataForStakeRegistration(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(StakeDeRegistration.class);

        stakeKeySteps.then_verifyStakeDeRegistrationResponseWithTotalPage(stakeDeRegistration,params);

    }
}
