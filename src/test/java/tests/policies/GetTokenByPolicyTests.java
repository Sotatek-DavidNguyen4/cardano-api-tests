package tests.policies;

import base.BaseTest;
import constants.Endpoints;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import microservices.policy.models.token.TokenByPolicy;
import microservices.policy.models.token.TokenByPolicyData;
import microservices.policy.steps.PolicySteps;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
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
        policySteps.verifyFormatOfGetTokensByPolicies(tokenByPolicyData)
                   .verifyPolicyInResponseDataOfToken(tokenByPolicyData,policyId)
                   .verifySizeResponseDataOfToken(tokenByPolicyData,1);
        }
    @DataProvider(name="getTokenByPolicies")
    public Object[][] getTokenByPolicies(){
        return new Object[][]{
                {"a0028f350aaabe0545fdcb56b039bfb08e4bb4d8c4d7c3c7d481c235"},
        };
    }

    @Test(description = "verify get list tokens by policies successfully", groups={"policy"},dataProvider = "getListTokenByPoliciesSuccess")
    public void getListTokenByPoliciesSuccess(String policyId){

        tokenByPolicy = (TokenByPolicy) policySteps.getTokenByPolicies(policyId)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TokenByPolicy.class);

        tokenByPolicyData = tokenByPolicy.getData();
        policySteps.verifySizeResponseDataOfToken(tokenByPolicyData,20)
                   .verifyResponseDataOfListTokenNotNull(tokenByPolicyData)
                   .verifyFormatOfGetTokensByPolicies(tokenByPolicyData)
                   .verifyPolicyInResponseDataOfToken(tokenByPolicyData,policyId);
    }

    @DataProvider(name="getListTokenByPoliciesSuccess")
    public Object[][] getListTokenByPoliciesSuccess(){
        return new Object[][]{
                {"4429f7b432125357388b1d676c2d503b6d6fc78c414934bef9882e26"},
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

    @Test(description = "verify get list tokens by policy with param", groups={"policy"},dataProvider = "getListTokenByPoliciesWithParams")
    public void getListTokenByPoliciesWithParam(Object page,Object size){
        String policyId ="4429f7b432125357388b1d676c2d503b6d6fc78c414934bef9882e26";

        MultiMap params = new MultiValueMap();
        params.put("page", page);
        params.put("size", size);

        tokenByPolicy = (TokenByPolicy) policySteps.getTokenByPoliciesWithParams(policyId,params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TokenByPolicy.class);

        tokenByPolicyData = tokenByPolicy.getData();
        policySteps.then_verifyTokenByPolicyResponse(tokenByPolicy,params)
                .verifyResponseDataOfListTokenNotNull(tokenByPolicyData)
                .verifyFormatOfGetTokensByPolicies(tokenByPolicyData)
                .verifyPolicyInResponseDataOfToken(tokenByPolicyData,policyId);;
    }

    @DataProvider(name="getListTokenByPoliciesWithParams")
    public Object[][] getListTokenByPoliciesWithParams(){
        return new Object[][]{
                {"10",""},
                {"abc",""},
                {"-10",""},
                {" ",""},
                {"@#$",""},
                {"","10"},
                {"","abc"},
                {"","-10"},
                {""," "},
                {"","!@@$$"},
        };
    }

    @Test(description = "verify get list tokens by policy with totalPage", groups={"policy"})
    public void getListTokenByPoliciesWithParamTotalPage(){
        String policyId ="4429f7b432125357388b1d676c2d503b6d6fc78c414934bef9882e26";
        tokenByPolicy = (TokenByPolicy) policySteps.getTokenByPolicies(policyId)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TokenByPolicy.class);
        MultiMap params = new MultiValueMap();
        params.put("page", tokenByPolicy.getTotalPages()+1);
        params.put("size", null);

        tokenByPolicy = (TokenByPolicy) policySteps.getTokenByPoliciesWithParams(policyId,params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(TokenByPolicy.class);

        tokenByPolicyData = tokenByPolicy.getData();
        policySteps.verifySizeResponseDataOfToken(tokenByPolicyData,0);

    }

}
