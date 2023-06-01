package tests.policies;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import microservices.common.constants.APIErrorCode;
import microservices.common.constants.APIErrorMessage;
import microservices.policy.models.holder.HolderByPolicy;
import microservices.policy.models.holder.HolderByPolicyData;
import microservices.policy.models.token.TokenByPolicy;
import microservices.policy.models.token.TokenByPolicyData;
import microservices.policy.steps.PolicySteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.util.List;

@Epic("cardano")
@Feature("api-policy_controller")
@Story("Get holders by policies")
public class GetHolderByPolicyTests extends BaseTest {
    private PolicySteps policySteps = new PolicySteps();
    private HolderByPolicy holderByPolicy = new HolderByPolicy();
    private List<HolderByPolicyData> holderByPolicyData;

    @Test(description = "verify get list holders by policies successfully", groups={"policy"},dataProvider = "getListHolderByPolicies")
    public void getHolderByPoliciesSuccess(String policyId){
        holderByPolicy = (HolderByPolicy) policySteps.getListHolderByPolicies(policyId)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(HolderByPolicy.class);

        holderByPolicyData = holderByPolicy.getData();
        policySteps.verifyResponseDataOfListHolderNotNull(holderByPolicyData)
                   .verifyFormatOfFingerprintGetHoldersByPolicy(holderByPolicyData);
    }
    @DataProvider(name="getListHolderByPolicies")
    public Object[][] getListHolderByPolicies(){
        return new Object[][]{
                {"fa1ab2cbdca59874005d9186b85245ed2503acaa63ab4121ab7c3879"},
        };
    }
    @Test(description = "verify get list holders by invalid policies ", groups={"policy"},dataProvider = "getListHolderByInPolicies")
    public void getHolderByInvalidPolicies(Object policyId){
        holderByPolicy =(HolderByPolicy) policySteps.getListHolderByPolicies(policyId)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(HolderByPolicy.class);
        policySteps.verifyHolderByPolicyResponse(holderByPolicy,true,0,0,0);
    }
    @DataProvider(name="getListHolderByInPolicies")
    public Object[][] getListHolderByInPolicies(){
        return new Object[][]{
                {123},
                {"@#$"},
                {" "},
                {"asset1c6t4elexwkpuzq08ssylhhmc78ahlz0sgw5a7y"},
                {"asset1c0vymmx0nysjaa8q5vah78jmuqyew3kjm48azr"},
        };
    }
}
