package microservices.policy.steps;

import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.common.steps.BaseSteps;
import microservices.policy.models.detail.PolicyDetail;
import microservices.policy.models.holder.HolderByPolicy;
import microservices.policy.models.holder.HolderByPolicyData;
import microservices.policy.models.token.TokenByPolicy;
import microservices.policy.models.token.TokenByPolicyData;
import org.testng.Assert;

import java.util.List;

public class PolicySteps extends BaseSteps {
    @Step("Get tokens by policies")
    public PolicySteps getTokenByPolicies(Object policyId){
        sendGet(Endpoints.PoliciesApi.GET_TOKEN_BY_POLICIES,Endpoints.PoliciesApi.POLICY_ID,policyId);
        return this;
    }
    @Step("Get list holder by policies")
    public PolicySteps getListHolderByPolicies(Object policyId){
        sendGet(Endpoints.PoliciesApi.GET_HOLDER_BY_POLICIES,Endpoints.PoliciesApi.POLICY_ID,policyId);
        return this;
    }
    @Step("Get a policy detail")
    public PolicySteps getPolicyDetail(Object policyId){
        sendGet(Endpoints.PoliciesApi.GET_POLICY_DETAIL,Endpoints.PoliciesApi.POLICY_ID,policyId);
        return this;
    }
    @Step("Verify response policy detail")
    public PolicySteps verifyResponsePolicyDetail(PolicyDetail policyDetail,Object policyId,Integer totalTokenExpected){
        Assert.assertEquals(policyDetail.getPolicyId(),policyId);
        Assert.assertEquals(policyDetail.getTotalToken(),totalTokenExpected);
        Assert.assertNotNull(policyDetail.getPolicyScript());
        return this;
    }
    @Step("Verify response data of token got by invalid policy")
    public PolicySteps verifyTokenByPolicyResponse(TokenByPolicy tokenByPolicy, boolean isEmpty, int totalItemsExpected, int totalPagesExpected, int currentPageExpected){
        Assert.assertEquals(tokenByPolicy.getData().isEmpty(),isEmpty);
        Assert.assertEquals(tokenByPolicy.getTotalItems(),totalItemsExpected);
        Assert.assertEquals(tokenByPolicy.getTotalPages(),totalPagesExpected);
        Assert.assertEquals(tokenByPolicy.getCurrentPage(),currentPageExpected);
        return this;
    }
    @Step("Verify response data of holder got by invalid policy")
    public PolicySteps verifyHolderByPolicyResponse(HolderByPolicy holderByPolicy, boolean isEmpty, int totalItemsExpected, int totalPagesExpected, int currentPageExpected){
        Assert.assertEquals(holderByPolicy.getData().isEmpty(),isEmpty);
        Assert.assertEquals(holderByPolicy.getTotalItems(),totalItemsExpected);
        Assert.assertEquals(holderByPolicy.getTotalPages(),totalPagesExpected);
        Assert.assertEquals(holderByPolicy.getCurrentPage(),currentPageExpected);
        return this;
    }
    @Step("Verify policy in response data of token got by policy")
    public PolicySteps verifyPolicyInResponseDataOfToken(List<TokenByPolicyData> listTokenByPolicyData, String policy){
        for (TokenByPolicyData tokenByPolicyData : listTokenByPolicyData){
            Assert.assertEquals(tokenByPolicyData.getPolicy(),policy);
        }
        return this;
    }
    @Step("Verify response data of token got by policy")
    public PolicySteps verifyResponseDataOfToken(List<TokenByPolicyData> tokenByPolicyData, String name, String displayName, Integer txCount){
        Assert.assertEquals(tokenByPolicyData.get(0).getName(),name);
        Assert.assertEquals(tokenByPolicyData.get(0).getDisplayName(),displayName);
        Assert.assertEquals(tokenByPolicyData.get(0).getTxCount(),txCount);
        return this;
    }
    @Step("Verify size of response data token got by policy")
    public PolicySteps verifySizeResponseDataOfToken(List<TokenByPolicyData> tokenByPolicyData, int size){
        Assert.assertEquals(tokenByPolicyData.size(),size);
        return this;
    }
    @Step("Verify response data of list token got by policy not null")
    public PolicySteps verifyResponseDataOfListTokenNotNull(List<TokenByPolicyData> listTokenByPolicyData){
        for (TokenByPolicyData tokenByPolicyData : listTokenByPolicyData){
            Assert.assertNotNull(tokenByPolicyData.getName());
            Assert.assertNotNull(tokenByPolicyData.getDisplayName());
            Assert.assertNotNull(tokenByPolicyData.getPolicy());
            Assert.assertNotNull(tokenByPolicyData.getFingerprint());
            Assert.assertNotNull(tokenByPolicyData.getTxCount());
            Assert.assertNotNull(tokenByPolicyData.getSupply());
            Assert.assertNotNull(tokenByPolicyData.getTotalVolume());
            Assert.assertNotNull(tokenByPolicyData.getCreatedOn());
        }
        return this;
    }
    @Step("Verify response data of list holder got by policy not null")
    public PolicySteps verifyResponseDataOfListHolderNotNull(List<HolderByPolicyData> listTokenByPolicyData){
        for (HolderByPolicyData holderByPolicyData : listTokenByPolicyData){
            Assert.assertNotNull(holderByPolicyData.getAddress());
            Assert.assertNotNull(holderByPolicyData.getName());
            Assert.assertNotNull(holderByPolicyData.getDisplayName());
            Assert.assertNotNull(holderByPolicyData.getFingerprint());
            Assert.assertNotNull(holderByPolicyData.getQuantity());

        }
        return this;
    }
}
