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

import static constants.AttributeFormats.STATKE_ADDRESS_LENGTH;
import static constants.Environment.isPreProd;


@Epic("cardano")
@Feature("api-stake-key-controller")
@Story("Get Top Delegators")
public class GetTopDelegatorsTest extends BaseTest {
    StakeKeySteps stakeKeySteps = new StakeKeySteps();
    TopDelegators topDelegators ;
    @Test(description = "Verify data for get top delegators",groups = {"stake","top_delegators"},dataProvider = "getParamForTopDelegators")
    public void getDataForTopDelegator(Object page,Object size){
        int length = isPreProd() ? STATKE_ADDRESS_LENGTH[0] : STATKE_ADDRESS_LENGTH[1];
        MultiMap params = new MultiValueMap();
        params.put("page", page);
        params.put("size", size);
        String pathSchema="schemaJson/stakes/stakeTopDelegator.json";
        topDelegators = (TopDelegators) stakeKeySteps.getTopDelegators(params)
                                                .validateResponse(HttpURLConnection.HTTP_OK)
                                                .validateResponseSchema(pathSchema)
                                                .saveResponseObject(TopDelegators.class);

        stakeKeySteps.then_verifyTopDelegatorsResponse(topDelegators,params)
                     .then_verifyFormatOfDelegatorsResponse(topDelegators.getData(),length)
                     .then_verifyGetTopDelegatorsResponseNotNull(topDelegators.getData())
                     .then_verifyBalanceTopDelegatorsResponseIsNotDecimal(topDelegators.getData());
    }
    @DataProvider(name = "getParamForTopDelegators")
    public Object[][] getParamForTopDelegators(){
        return new Object[][]{
                {"",""},
                /**
                 * bug with param page : ADAE-561
                */
//                {"1",""},
//                {"20",""},
//                {"abc",""},
//                {"-10",""},
//                {" ",""},
//                {"@#$%%",""},
                {"","100"},
                {"","abc"},
                {"","-10"},
                {""," "},
                {"","!@@$$"},
        };
    }
}
