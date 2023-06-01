package tests.policies;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import microservices.policy.models.token.TokenByPolicy;
import microservices.policy.models.token.TokenByPolicyData;
import microservices.policy.steps.PolicySteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.util.List;

@Epic("cardano")
@Feature("api-policy_controller")
@Story("Get tokens by policies")
public class GetTokenByPolicyTests extends BaseTest {
    private PolicySteps policySteps = new PolicySteps();
    private TokenByPolicy tokenByPolicy = new TokenByPolicy();
    private List<TokenByPolicyData> tokenByPolicyData;

    @Test(description = "verify get tokens by policies successfully", groups={"policy"},dataProvider = "getTokenByPolicies")
    public void getTokenByPoliciesSuccess(String policyId){
        tokenByPolicy = (TokenByPolicy) policySteps.getTokenByPolicies(policyId)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TokenByPolicy.class);

        tokenByPolicyData = tokenByPolicy.getData();
        policySteps.verifyResponseDataOfToken(tokenByPolicyData,"4b555431","KUT1",109954)
                   .verifyFormatOfGetTokensByPolicies(tokenByPolicyData)
                   .verifyPolicyInResponseDataOfToken(tokenByPolicyData,policyId);
        }
    @DataProvider(name="getTokenByPolicies")
    public Object[][] getTokenByPolicies(){
        return new Object[][]{
                {"e38748c08c510a4a5d712922a0f91269b8446ac565068f653c517475"},
        };
    }

    @Test(description = "verify get list tokens by policies successfully", groups={"policy"},dataProvider = "getListTokenByPoliciesSuccess")
    public void getListTokenByPoliciesSuccess(String policyId){

        tokenByPolicy = (TokenByPolicy) policySteps.getTokenByPolicies(policyId)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TokenByPolicy.class);

        tokenByPolicyData = tokenByPolicy.getData();
        policySteps.verifySizeResponseDataOfToken(tokenByPolicyData,3)
                   .verifyResponseDataOfListTokenNotNull(tokenByPolicyData)
                   .verifyFormatOfGetTokensByPolicies(tokenByPolicyData)
                   .verifyPolicyInResponseDataOfToken(tokenByPolicyData,policyId);
    }

    @DataProvider(name="getListTokenByPoliciesSuccess")
    public Object[][] getListTokenByPoliciesSuccess(){
        return new Object[][]{
                {"fa1ab2cbdca59874005d9186b85245ed2503acaa63ab4121ab7c3879"},
        };
    }

    @Test(description = "verify get list tokens by Invalid policy", groups={"policy"},dataProvider = "getListTokenByInvalidPolicy")
    public void getListTokenByInvalidPolicies(Object policyId){

        tokenByPolicy = (TokenByPolicy) policySteps.getTokenByPolicies(policyId)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TokenByPolicy.class);

        policySteps.verifyTokenByPolicyResponse(tokenByPolicy,true,0,0,0);
    }

    @DataProvider(name="getListTokenByInvalidPolicy")
    public Object[][] getListTokenByInvalidPolicy(){
        return new Object[][]{
                {123},
                {"@#$"},
                {" "},
                {"asset1c6t4elexwkpuzq08ssylhhmc78ahlz0sgw5a7y"},
                {"asset1c0vymmx0nysjaa8q5vah78jmuqyew3kjm48azr"},
        };
    }
}
