package tests.stakes;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import microservices.stakeKey.models.registration.StakeRegistration;
import microservices.stakeKey.models.topDelegators.TopDelegators;
import microservices.stakeKey.steps.StakeKeySteps;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;


@Epic("cardano")
@Feature("api-stake-key-controller")
@Story("Get Top Delegators")
public class GetTopDelegatorsTest extends BaseTest {
    StakeKeySteps stakeKeySteps = new StakeKeySteps();
    TopDelegators topDelegators ;
    @Test(description = "Verify data for get top delegators",groups = "stake-key-controller",dataProvider = "getParamForTopDelegators")
    public void getDataForStakeDeRegistration(Object page,Object size){
        MultiMap params = new MultiValueMap();
        params.put("page", page);
        params.put("size", size);
        topDelegators = (TopDelegators) stakeKeySteps.getTopDelegators(params)
                                                .validateResponse(HttpURLConnection.HTTP_OK)
                                                .saveResponseObject(TopDelegators.class);

        stakeKeySteps.then_verifyTopDelegatorsResponse(topDelegators,params)
                     .then_verifyFormatOfDelegatorsResponse(topDelegators.getData())
                     .then_verifyGetTopDelegatorsResponseNotNull(topDelegators.getData());
    }
    @DataProvider(name = "getParamForTopDelegators")
    public Object[][] getParamForTopDelegators(){
        return new Object[][]{
                {"",""},
                {"0","20"},
                {"20",""},
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
