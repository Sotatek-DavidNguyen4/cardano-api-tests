package tests;

import base.BaseTest;
import constants.Endpoints;
import microservices.stakeKey.steps.StakeKeySteps;
import org.testng.annotations.Test;

public class StakeKeyTests extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    @Test(description = "get a stake detail by address", groups = {"stakeKey"})
    public void getStakeByAddress(){
        stakeKeySteps.getStakeByAddress("addr_test1qr53akzyd4949txn5hu583yu0xatcvp2efec9tm56jpeg6xkfjf77qy57hqhnefcqyy7hmhsygj9j38rj984hn9r57fsq48dyr")
                .validateStatusCode(200);
    }
}
