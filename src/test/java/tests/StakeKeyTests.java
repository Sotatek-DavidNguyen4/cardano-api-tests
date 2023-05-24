package tests;

import base.BaseTest;
import microservices.stakeKey.constants.StakeKeyConstants;
import microservices.stakeKey.steps.StakeKeySteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;
public class StakeKeyTests extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    private String address = "addr_test1qr53akzyd4949txn5hu583yu0xatcvp2efec9tm56jpeg6xkfjf77qy57hqhnefcqyy7hmhsygj9j38rj984hn9r57fsq48dyr";

    @Test(description = "get a stake detail by address", groups = {"stakeKey"})
    public void getStakeByAddress(){
        stakeKeySteps.getStakeByAddress(address)
                .validateStatusCode(HttpURLConnection.HTTP_OK);
    }
    @Test(description = "get stake detail by address wrong format", groups = {"stakeKey"}, dataProvider = "listAddressWrongFormat")
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
    @Test(description = "get stake instantaneous rewards with stake key", groups = {"stakeKey"}, dataProvider = "stakeKeyInstantaneousReward")
    public void getStakeInstantaneousReward(Object stakeKey){
        stakeKeySteps.getStakeInstantaneousRewards(stakeKey)
                .validateStatusCode(HttpURLConnection.HTTP_OK);
    }
    @DataProvider(name = "stakeKeyInstantaneousReward")
    public Object[][] DatasetStakeKeyInstantaneousRewards(){
        return new Object[][]{
                {"stake_test1urz3dwcwdhvl9dy75rj5krvpmjyldeyxqx5p6xms6xyjd7quqmq2p"},
//                {"@#$%"},
                {"  "},
                {"abcd"},
                {"12345"}
        };
    }
    @Test(description = "get stake instantaneous rewards with param", groups = "stakeKey", dataProvider = "paramInstantaneousReward")
    public void getStakkeInstantaneousRewardParam(Object page, Object size){
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .getParamsMap();
        String stakeKey = "stake_test1urz3dwcwdhvl9dy75rj5krvpmjyldeyxqx5p6xms6xyjd7quqmq2p";
        stakeKeySteps.getStakeInstantaneousRewardParam(stakeKey, param)
                .validateStatusCode(HttpURLConnection.HTTP_OK);
    }
    @DataProvider(name = "paramInstantaneousReward")
    public Object[][] DatasetParamStakeInstantaneousReward(){
        return new Object[][]{
//                {9, null},
                {"abc", null},
                {-10, null},
                {" ", null},
                {"@#$%", null},

                {null, 1},
                {null, "abc"},
                {null, -10},
                {null, " "},
                {null, "@#$%"}
        };
    }
}
