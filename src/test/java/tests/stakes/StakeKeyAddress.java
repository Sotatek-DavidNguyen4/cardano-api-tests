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

public class StakeKeyAddress extends BaseTest {
    private StakeKeySteps stakeKeySteps = new StakeKeySteps();
    private String address = "addr1q8pusk379txj05qkqefk6yh5ah757qqt0cshserew99fks2kps3gp6k8clda0tgch7m9a9r3z9ws84x637sac045q37q6tdtvz";
    private String stakeAddress = "stake1u9tqcg5qatru0k7h45vtldj7j3c3zhgr6ndglgwu866qglqkzv6c2";
    @Test(description = "get a stake detail by address", groups = {"stake", "stake_address"})
    public void getStakeByAddress(){
        String pathStakeAddressSchema = "schemaJson/stakes/stakeAddress.json";
        int length = isPreProd() ? STATKE_ADDRESS_LENGTH[0] : STATKE_ADDRESS_LENGTH[1];
        StakeModel stakeModel = (StakeModel)
        stakeKeySteps.getStakeByAddress(address)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(StakeModel.class);

        ArrayList<Object> elements = new ArrayList<>();
        elements.add(stakeModel.getTotalStake());
        elements.add(stakeModel.getRewardAvailable());
        elements.add(stakeModel.getRewardWithdrawn());

        stakeKeySteps.verifyGetStakeAddressResponse(stakeModel, length, stakeAddress)
                 .verifyElementsIsNotDecimal(elements)
                .validateResponseSchema(pathStakeAddressSchema);
    }
    @Test(description = "get stake detail by address wrong format", groups = {"stake", "stake_address"}, dataProvider = "listAddressWrongFormat")
    public void getStakeByAddresWrongFormat(Object address){
        stakeKeySteps.getStakeByAddress(address)
                .then_verifyErrorResponse(HttpURLConnection.HTTP_BAD_REQUEST, StakeKeyConstants.ERROR_MESSAGE,StakeKeyConstants.ERROR_CODE);
    }
    @DataProvider(name = "listAddressWrongFormat")
    public Object[][] DatasetListAddress(){
        return new Object[][]{
                {"@#$"},
                {"  "},
                {"abc"},
                {"12345"}
        };
    }
}

