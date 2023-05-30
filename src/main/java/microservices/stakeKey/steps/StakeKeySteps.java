package microservices.stakeKey.steps;

import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.common.constants.RequestParams;
import microservices.common.steps.BaseSteps;
import microservices.stakeKey.models.StakeModel;
import microservices.stakeKey.models.deRegistration.StakeDeRegistration;
import microservices.stakeKey.models.StakeHistory;
import microservices.stakeKey.models.deRegistration.StakeDeRegistrationData;
import microservices.stakeKey.models.registration.StakeRegistration;
import microservices.stakeKey.models.registration.StakeRegistrationData;
import microservices.stakeKey.models.topDelegators.TopDelegators;
import microservices.stakeKey.models.topDelegators.TopDelegatorsData;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StakeKeySteps extends BaseSteps {
    @Step("get a stake detail by address")
    public StakeKeySteps getStakeByAddress(Object address){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE_ADDRESS, "address", address);
        return this;
    }
    @Step("verify response of get stake address")
    public StakeKeySteps verifyResponseStakeAddress(StakeModel stakeModel, String stakeAddress){
        assertThat(stakeModel.getStakeAddress()).isEqualTo(stakeAddress);
        return this;
    }
    @Step("get a stake instantaneous-rewards with stakeKey")
    public StakeKeySteps getStakeInstantaneousRewards(Object stakeKey){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE_INSTANTANEOUS_REWARDS, Endpoints.StakeKeyApi.STAKE_KEY, stakeKey);
        return this;
    }
    @Step("get a stake instantaneous rewards with param")
    public StakeKeySteps getStakeInstantaneousRewardParam(Object stakeKey, Map<String, Object> param){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE_INSTANTANEOUS_REWARDS, param, Endpoints.StakeKeyApi.STAKE_KEY, stakeKey);
        return this;
    }
    @Step("Get top delegators")
    public StakeKeySteps getTopDelegators(Map<String, Object> param){
        sendGet(Endpoints.StakeKeyApi.GET_TOP_DELEGATORS, param);
        return this;
    }
    @Step("get Data For Stake Registration with param")
    public StakeKeySteps getDataForStakeRegistration(Map<String, Object> param){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE_REGISTRATION, param);
        return this;
    }
    @Step("get Data For Stake De Registration with param")
    public StakeKeySteps getDataForStakeDeRegistration(Map<String, Object> param){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE_DE_REGISTRATION, param);
        return this;
    }
    @Step("get active stake, live stake and total stake")
    public StakeKeySteps getStakeAnalytics(){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE_ANALYTICS);
        return this;
    }
    @Step("Verify currentPage and size of get top delegators response")
    public StakeKeySteps then_verifyTopDelegatorsResponse(TopDelegators topDelegators, Map<String, Object> params) {
        RequestParams requestParams = new RequestParams(params, 0, 20);
        assertThat(topDelegators.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        assertThat(topDelegators.getData().size())
                .as("The size of page is wrong")
                .isEqualTo(requestParams.getSize());
        return this;
    }
    @Step("Verify currentPage and size of get Data For Stake Registration response")
    public StakeKeySteps then_verifyStakeRegistrationResponse(StakeRegistration stakeRegistration, Map<String, Object> params) {
        RequestParams requestParams = new RequestParams(params, 0, 20);
        assertThat(stakeRegistration.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        assertThat(stakeRegistration.getData().size())
                .as("The size of page is wrong")
                .isEqualTo(requestParams.getSize());
        return this;
    }
    @Step("Verify currentPage and size of get Data For Stake De Registration response")
    public StakeKeySteps then_verifyStakeDeRegistrationResponse(StakeDeRegistration stakeDeRegistration, Map<String, Object> params) {
        RequestParams requestParams = new RequestParams(params, 0, 20);
        assertThat(stakeDeRegistration.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        assertThat(stakeDeRegistration.getData().size())
                .as("The size of page is wrong")
                .isEqualTo(requestParams.getSize());
        return this;
    }
    @Step("Verify data for get top delegators response not null")
    public StakeKeySteps then_verifyGetTopDelegatorsResponseNotNull(List<TopDelegatorsData> topDelegatorsDataList) {
        for (TopDelegatorsData topDelegatorsData:topDelegatorsDataList){
            Assert.assertNotNull(topDelegatorsData.getBalance());
            Assert.assertNotNull(topDelegatorsData.getPoolId());
            Assert.assertNotNull(topDelegatorsData.getStakeKey());
        }
        return this;
    }
    @Step("Verify Data For Stake Registration response not null")
    public StakeKeySteps then_verifyStakeRegistrationResponseNotNull(List<StakeRegistrationData> stakeRegistrationDatas) {
        for (StakeRegistrationData stakeRegistrationData:stakeRegistrationDatas){
            Assert.assertNotNull(stakeRegistrationData.getTxId());
            Assert.assertNotNull(stakeRegistrationData.getTxHash());
            Assert.assertNotNull(stakeRegistrationData.getTxTime());
            Assert.assertNotNull(stakeRegistrationData.getBlock());
            Assert.assertNotNull(stakeRegistrationData.getEpoch());
            Assert.assertNotNull(stakeRegistrationData.getSlotNo());
            Assert.assertNotNull(stakeRegistrationData.getEpochSlotNo());
            Assert.assertNotNull(stakeRegistrationData.getStakeKey());
        }
        return this;
    }
    @Step("Verify Data For Stake De Registration response not null")
    public StakeKeySteps then_verifyStakeDeRegistrationResponseNotNull(List<StakeDeRegistrationData> stakeDeRegistrationDatas) {
        for (StakeDeRegistrationData stakeDeRegistrationData:stakeDeRegistrationDatas){
            Assert.assertNotNull(stakeDeRegistrationData.getTxId());
            Assert.assertNotNull(stakeDeRegistrationData.getTxHash());
            Assert.assertNotNull(stakeDeRegistrationData.getTxTime());
            Assert.assertNotNull(stakeDeRegistrationData.getBlock());
            Assert.assertNotNull(stakeDeRegistrationData.getEpoch());
            Assert.assertNotNull(stakeDeRegistrationData.getSlotNo());
            Assert.assertNotNull(stakeDeRegistrationData.getEpochSlotNo());
            Assert.assertNotNull(stakeDeRegistrationData.getStakeKey());
        }
        return this;
    }

    @Step("get a stake with stakeKey")
    public StakeKeySteps getStakeWithStakeKey(Object stakeKey){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE, Endpoints.StakeKeyApi.STAKE_KEY, stakeKey);
        return this;
    }
    @Step("verify response of get stake")
    public StakeKeySteps verifyResponseStake(StakeModel stakeModel, String stakeKey, String poolId){
        assertThat(stakeModel.getStakeAddress()).isEqualTo(stakeKey);
        assertThat(stakeModel.getPool().getPoolId()).isEqualTo(poolId);
        return this;
    }
    @Step("get stake history")
    public StakeKeySteps getStakeHistory(Object stakeKey){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE_HISTORY, Endpoints.StakeKeyApi.STAKE_KEY, stakeKey);
        return this;
    }
    @Step("get stake history all key")
    public StakeKeySteps getStakeHistoryAllKey(Object stakeKey, Map<String, Object> param){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE_HISTORY, param,  Endpoints.StakeKeyApi.STAKE_KEY, stakeKey);
        return this;
    }
    @Step("verify that current page")
    public StakeKeySteps then_verifyFilterStakeHistoryResponse(StakeHistory stakeHistory, Map<String,Object> param){
        RequestParams requestParams = new RequestParams(param, 0, 20);
        assertThat(stakeHistory.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        return this;
    }
}
