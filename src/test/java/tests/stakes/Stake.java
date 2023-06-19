package tests.stakes;

import base.BaseTest;
import microservices.stakeKey.constants.StakeKeyConstants;
import microservices.stakeKey.models.StakeModel;
import microservices.stakeKey.steps.StakeKeySteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static constants.AttributeFormats.STATKE_ADDRESS_LENGTH;
import static constants.Environment.isPreProd;

public class Stake extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    private String stakeKey = "stake1u9pwlay8fvev5yfvnpx8c057n3m0aa5g493qw44zqdycz6sm0cph9";
    @Test(description = "get stake with stake key", groups = {"stake", "stake_key"})
    public void getStake(){
        int length = isPreProd() ? STATKE_ADDRESS_LENGTH[0] : STATKE_ADDRESS_LENGTH[1];
        Map<String, Object> expect = new HashMap<>();
        expect.put("stakeAddress", "stake1u9pwlay8fvev5yfvnpx8c057n3m0aa5g493qw44zqdycz6sm0cph9");
        expect.put("tickerName", "SPIRE");
        expect.put("poolName", "Spire Staking | \uD83C\uDFC6 Top 10 Operator");
        expect.put("poolId", "pool16agnvfan65ypnswgg6rml52lqtcqe5guxltexkn82sqgj2crqtx");

        StakeModel stakeModel = (StakeModel)
        stakeKeySteps.getStakeWithStakeKey(stakeKey)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(StakeModel.class);

        ArrayList<Object> elements = new ArrayList<>();
        elements.add(stakeModel.getTotalStake());
        elements.add(stakeModel.getRewardAvailable());
        elements.add(stakeModel.getRewardWithdrawn());

        stakeKeySteps.verifyGetStakeResponse(stakeModel, length, expect)
                .verifyElementsIsNotDecimal(elements);
    }
    @Test(description = "get stake with stake key | unsuccess", groups = {"stake", "stake_key"}, dataProvider = "stakeKey")
    public void getStakeUnsuccess(Object stakeKey){
        stakeKeySteps.getStakeWithStakeKey(stakeKey)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, StakeKeyConstants.ERROR_MESSAGE, StakeKeyConstants.ERROR_CODE);
    }
    @DataProvider(name = "stakeKey")
    public Object[][] DatasetStakeKey(){
        return new Object[][]{
                {"stake1uzrrkmef4v2x9xfrlycckjhgpgdrnwj45t0rdmjxn5gv66swg0ay9"},
                {"@#$"},
                {"  "},
                {"abcd"},
                {"12345"}
        };
    }
}
