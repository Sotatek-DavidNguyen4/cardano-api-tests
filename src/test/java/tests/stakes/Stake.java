package tests.stakes;

import base.BaseTest;
import microservices.stakeKey.constants.StakeKeyConstants;
import microservices.stakeKey.models.StakeModel;
import microservices.stakeKey.steps.StakeKeySteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;

import static constants.AttributeFormats.STATKE_ADDRESS_LENGTH;
import static constants.Environment.isPreProd;

public class Stake extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    private String stakeKey = "stake1u9pwlay8fvev5yfvnpx8c057n3m0aa5g493qw44zqdycz6sm0cph9";
    @Test(description = "get stake with stake key", groups = {"stake", "stake_key"})
    public void getStake(){
        int length = isPreProd() ? STATKE_ADDRESS_LENGTH[0] : STATKE_ADDRESS_LENGTH[1];
        StakeModel stakeModel = (StakeModel)
        stakeKeySteps.getStakeWithStakeKey(stakeKey)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(StakeModel.class);
        stakeKeySteps.verifyResponseStake(stakeModel, length);
    }
    @Test(description = "get stake with stake key | unsuccess", groups = {"stake", "stake_key"}, dataProvider = "stakeKey")
    public void getStakeUnsuccess(Object stakeKey){
        stakeKeySteps.getStakeWithStakeKey(stakeKey)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, StakeKeyConstants.ERROR_MESSAGE, StakeKeyConstants.ERROR_CODE);
    }
    @DataProvider(name = "stakeKey")
    public Object[][] DatasetStakeKey(){
        return new Object[][]{
                {"stake_test1urf6y7ktcqwzxd2tc3x54437jl6vcqvazgrdka3v2njdjzgyn6hgy"},
                {"@#$"},
                {"  "},
                {"abcd"},
                {"12345"}
        };
    }
}
