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
@Story("Get Data For Stake Registration")
public class GetDataForStakeRegistrationTest extends BaseTest {
    StakeKeySteps stakeKeySteps = new StakeKeySteps();
    StakeRegistration stakeRegistration ;
    @Test(description = "Verify data for stake registration",groups = "stake-key-controller",dataProvider = "getParamForStakeRegistration")
    public void getDataForStakeDeRegistration(Object page,Object size){
        int length = isPreProd() ? STATKE_ADDRESS_LENGTH[0] : STATKE_ADDRESS_LENGTH[1];
        MultiMap params = new MultiValueMap();
        params.put("page", page);
        params.put("size", size);
        stakeRegistration = (StakeRegistration) stakeKeySteps.getDataForStakeRegistration(params)
                                                .validateResponse(HttpURLConnection.HTTP_OK)
                                                .saveResponseObject(StakeRegistration.class);

        stakeKeySteps.then_verifyStakeRegistrationResponse(stakeRegistration,params)
                     .then_verifyFormatOfStakeRegistrationResponse(stakeRegistration.getData(),length)
                     .then_verifyStakeRegistrationResponseNotNull(stakeRegistration.getData());
    }
    @DataProvider(name = "getParamForStakeRegistration")
    public Object[][] getParamForStakeRegistration(){
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
