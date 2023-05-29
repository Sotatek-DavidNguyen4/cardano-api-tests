package tests.stakes;


import base.BaseTest;
import microservices.stakeKey.constants.StakeKeyConstants;
import microservices.stakeKey.models.StakeModel;
import microservices.stakeKey.steps.StakeKeySteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class StakeKeyAddress extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    private String address = "addr_test1qr53akzyd4949txn5hu583yu0xatcvp2efec9tm56jpeg6xkfjf77qy57hqhnefcqyy7hmhsygj9j38rj984hn9r57fsq48dyr";
    private String stakeAddress = "stake_test1urtyeyl0qz20tsteu5uqzz0tamczyfzegn3ezn6mej360ycky7cg5";
    @Test(description = "get a stake detail by address", groups = {"stake", "stake_address"})
    public void getStakeByAddress(){
        Map<String, Object> expected = new HashMap<>();
        expected.put("stakeAddress", stakeAddress);
        StakeModel stakeModel = (StakeModel)
        stakeKeySteps.getStakeByAddress(address)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(StakeModel.class);
        stakeKeySteps.verifyResponseStakeAddress(stakeModel, expected);
    }
    @Test(description = "get stake detail by address wrong format", groups = {"stake", "stake_address"}, dataProvider = "listAddressWrongFormat")
    public void getStakeByAddresWrongFormat(Object address){
        stakeKeySteps.getStakeByAddress(address)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, StakeKeyConstants.ERROR_MESSAGE,StakeKeyConstants.ERROR_CODE);
    }
    @DataProvider(name = "listAddressWrongFormat")
    public Object[][] DatasetListAddress(){
        return new Object[][]{
                {"FHnt4NL7yPYH2vP2FLEfH2pt3K6meM7fgtjRiLBidaqpP5ogPzxLNsZy68e1KdW"},
//                {"@#$%"},
                {"  "},
                {"abc"},
                {1234}
        };
    }
}

