package microservices.stakeKey.steps;

import constants.Endpoints;
import io.qameta.allure.Step;
import microservices.common.constants.RequestParams;
import microservices.common.steps.BaseSteps;
import microservices.stakeKey.models.Analytics.StakeAnalytics;
import microservices.stakeKey.models.DataHistory;
import microservices.stakeKey.models.StakeModel;
import microservices.stakeKey.models.deRegistration.StakeDeRegistration;
import microservices.stakeKey.models.StakeHistory;
import microservices.stakeKey.models.deRegistration.StakeDeRegistrationData;
import microservices.stakeKey.models.history.DelegationHistoryDataModel;
import microservices.stakeKey.models.history.DelegationHistoryModel;
import microservices.stakeKey.models.history.WithdrawalHistoryDataModel;
import microservices.stakeKey.models.instantaneousReward.InstantaneousRewardDataModel;
import microservices.stakeKey.models.instantaneousReward.InstantaneousRewardModel;
import microservices.stakeKey.models.listAddress.StakeListAddressModel;
import microservices.stakeKey.models.registration.StakeRegistration;
import microservices.stakeKey.models.registration.StakeRegistrationData;
import microservices.stakeKey.models.topDelegators.TopDelegators;
import microservices.stakeKey.models.topDelegators.TopDelegatorsData;
import microservices.stakeKey.models.history.WithdrawalHistoryModel;
import org.testng.Assert;
import util.AttributeStandard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static constants.DateFormats.DATE_FORMAT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static util.AttributeStandard.*;

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
    @Step("verify that current page instantaneous reward")
    public StakeKeySteps then_verifyFilterInstantaneousResponse(InstantaneousRewardModel instantaneousRewardModel, Map<String,Object> param){
        RequestParams requestParams = new RequestParams(param, 0, 20);
        assertThat(instantaneousRewardModel.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        return this;
    }
    @Step("verify response stake instantaneous rewards")
    public StakeKeySteps verifyStakeInstantaneousRewards(ArrayList<InstantaneousRewardDataModel> data){
        for(InstantaneousRewardDataModel a : data){
            Assert.assertTrue(AttributeStandard.isValidDateFormat(a.getTime(), DATE_FORMAT[0]));
            Assert.assertTrue(AttributeStandard.isValidHash(a.getTxHash()));
        }
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

    @Step("Verify size of get Data For Stake Registration response with totalPage")
    public StakeKeySteps then_verifyStakeRegistrationResponseWithTotalPage(StakeRegistration stakeRegistration, Map<String, Object> params) {
        RequestParams requestParams = new RequestParams(params, 0, 0);
        assertThat(stakeRegistration.getData().size())
                .as("The size of page is wrong")
                .isEqualTo(requestParams.getSize());

        return this;
    }
    @Step("Verify size of get Data For Stake De Registration response with totalPage")
    public StakeKeySteps then_verifyStakeDeRegistrationResponseWithTotalPage(StakeDeRegistration stakeDeRegistration, Map<String, Object> params) {
        RequestParams requestParams = new RequestParams(params, 0, 0);
        assertThat(stakeDeRegistration.getData().size())
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
    @Step("Verify balance for get top delegators response is not decimal")
    public StakeKeySteps then_verifyBalanceTopDelegatorsResponseIsNotDecimal(List<TopDelegatorsData> topDelegatorsDataList) {
        for (TopDelegatorsData topDelegatorsData:topDelegatorsDataList){
            Assert.assertTrue(AttributeStandard.isNotDecimal(topDelegatorsData.getBalance()));
        }
        return this;
    }
    @Step("Verify format of top delegators response")
    public StakeKeySteps then_verifyFormatOfDelegatorsResponse(List<TopDelegatorsData> topDelegatorsDataList,int length) {
        for (TopDelegatorsData topDelegatorsData:topDelegatorsDataList){
            Assert.assertTrue(isValidStakeAddress(topDelegatorsData.getStakeKey(),length));
            Assert.assertTrue(isValidPoolId(topDelegatorsData.getPoolId()));
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
    @Step("Verify Data For Stake Analytics response not null")
    public StakeKeySteps then_verifyStakeAnalyticsResponseNotNull(StakeAnalytics stakeAnalytics) {
            Assert.assertNotNull(stakeAnalytics.getActiveStake());
            Assert.assertNotNull(stakeAnalytics.getLiveStake());
        return this;
    }
    @Step("Verify format of Stake Registration response")
    public StakeKeySteps then_verifyFormatOfStakeRegistrationResponse(List<StakeRegistrationData> stakeRegistrationDatas ,int length) {
        for (StakeRegistrationData stakeRegistrationData:stakeRegistrationDatas){
            Assert.assertTrue(isValidHash(stakeRegistrationData.getTxHash()));
            Assert.assertTrue(isValidDateFormat(stakeRegistrationData.getTxTime(),DATE_FORMAT[0]));
            Assert.assertTrue(isValidStakeAddress(stakeRegistrationData.getStakeKey(),length));
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
    @Step("Verify format of Stake De Registration response")
    public StakeKeySteps then_verifyFormatOfStakeDeRegistrationResponse(List<StakeDeRegistrationData> stakeDeRegistrationDatas,int length) {
        for (StakeDeRegistrationData stakeDeRegistrationData:stakeDeRegistrationDatas){
            Assert.assertTrue(isValidHash(stakeDeRegistrationData.getTxHash()));
            Assert.assertTrue(isValidDateFormat(stakeDeRegistrationData.getTxTime(),DATE_FORMAT[0]));
            Assert.assertTrue(isValidStakeAddress(stakeDeRegistrationData.getStakeKey(),length));
        }
        return this;
    }

    @Step("get a stake with stakeKey")
    public StakeKeySteps getStakeWithStakeKey(Object stakeKey){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE, Endpoints.StakeKeyApi.STAKE_KEY, stakeKey);
        return this;
    }
    @Step("verify get detail stake of response")
    public StakeKeySteps verifyGetStakeResponse(StakeModel stakeModel, int length, Map<String, Object> expect){
        Assert.assertEquals(stakeModel.getStakeAddress(), expect.get("stakeAddress"));
        Assert.assertEquals(stakeModel.getPool().getTickerName(), expect.get("tickerName"));
        Assert.assertEquals(stakeModel.getPool().getPoolName(), expect.get("poolName"));
        Assert.assertEquals(stakeModel.getPool().getPoolId(), expect.get("poolId"));
        Assert.assertTrue(AttributeStandard.isValidStakeAddress(stakeModel.getStakeAddress(), length));
        Assert.assertTrue(AttributeStandard.isValidPoolId(stakeModel.getPool().getPoolId()));
        return this;
    }
    @Step("check elements is not decimal")
    public StakeKeySteps verifyElementsIsNotDecimal(ArrayList<Object> elements){
        for (Object element : elements){
            String temp = String.valueOf(element);
            Assert.assertFalse(temp.contains(".") || temp.contains(","));
        }
        return this;
    }
    @Step("verify get stake address of response")
    public StakeKeySteps verifyGetStakeAddressResponse(StakeModel stakeModel, int length, String stakeAddress){
        Assert.assertEquals(stakeModel.getStakeAddress(), stakeAddress);
        Assert.assertTrue(AttributeStandard.isValidStakeAddress(stakeModel.getStakeAddress(), length));
        Assert.assertTrue(AttributeStandard.isValidPoolId(stakeModel.getPool().getPoolId()));
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
    public StakeKeySteps then_verifyPageStakeHistoryResponse(StakeHistory stakeHistory, Map<String,Object> param){
        RequestParams requestParams = new RequestParams(param, 0);
        assertThat(stakeHistory.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        return this;
    }
    @Step("verify that size of response")
    public StakeKeySteps then_verifySizeStakeHistoryResponse(StakeHistory stakeHistory, Map<String,Object> param, int sizeDefalt){
        RequestParams requestParams = new RequestParams(param, 0, sizeDefalt);
        assertThat(stakeHistory.getData().size())
                .as("Value of field 'size' is wrong")
                .isEqualTo(requestParams.getSize());
        return this;
    }
    @Step("verify response stake history")
    public StakeKeySteps verifyStakeHistory(ArrayList<DataHistory> data){
        for(DataHistory a : data){
            Assert.assertTrue(AttributeStandard.isValidDateFormat(a.getTime(), DATE_FORMAT[0]));
            Assert.assertTrue(AttributeStandard.isValidHash(a.getTxHash()));
        }
        return this;
    }
    @Step("get stake key withdrawal history")
    public StakeKeySteps getStakeKeyWithdrawalHistory(Object stakeKey, Map<String, Object> param){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE_WITHDRAWAL_HISTORY, param,  Endpoints.StakeKeyApi.STAKE_KEY, stakeKey);
        return this;
    }
    @Step("verify that current page of stake withdrawal history")
    public StakeKeySteps then_verifyPageStakeWithdrawalHistoryResponse(WithdrawalHistoryModel withdrawalHistoryModel, Map<String,Object> param){
        RequestParams requestParams = new RequestParams(param, 0);
        assertThat(withdrawalHistoryModel.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        return this;
    }
    @Step("verify that size of stake withdrawal history")
    public StakeKeySteps then_verifySizeStakeWithdrawalHistoryResponse(WithdrawalHistoryModel withdrawalHistoryModel, Map<String,Object> param, int defaultSize){
        RequestParams requestParams = new RequestParams(param, 0, defaultSize);
        assertThat(withdrawalHistoryModel.getData().size())
                .as("Value of field 'size' is wrong")
                .isEqualTo(requestParams.getSize());
        return this;
    }
    @Step("verify response stake withdrawal history")
    public StakeKeySteps verifyStakeWithdrawalHistory(ArrayList<WithdrawalHistoryDataModel> data){
        for(WithdrawalHistoryDataModel a : data){
            Assert.assertTrue(AttributeStandard.isValidDateFormat(a.getTime(), DATE_FORMAT[0]));
            Assert.assertTrue(AttributeStandard.isValidHash(a.getTxHash()));
        }
        return this;
    }
    @Step("get stake delegation history")
    public StakeKeySteps getDelegationHistory(String stakeKey, Map<String, Object> param){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE_DELEGATION_HISTORY, param,  Endpoints.StakeKeyApi.STAKE_KEY, stakeKey);
        return this;
    }
    @Step("verify that size of stake delegation history")
    public StakeKeySteps then_verifySizeDelegationHistoryResponse(DelegationHistoryModel delegationHistoryModel, Map<String,Object> param, int defaultSize){
        RequestParams requestParams = new RequestParams(param, 0, defaultSize);
        assertThat(delegationHistoryModel.getData().size())
                .as("Value of field 'size' is wrong")
                .isEqualTo(requestParams.getSize());
        return this;
    }
    @Step("verify that current page of stake delegation history")
    public StakeKeySteps then_verifyCurrentPageDelegationHistoryResponse(DelegationHistoryModel delegationHistoryModel, Map<String,Object> param){
        RequestParams requestParams = new RequestParams(param, 0);
        assertThat(delegationHistoryModel.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        return this;
    }
    @Step("verify response stake delegation history")
    public StakeKeySteps verifyStakeDelegationHistory(ArrayList<DelegationHistoryDataModel> data){
        for(DelegationHistoryDataModel a : data){
            Assert.assertTrue(AttributeStandard.isValidDateFormat(a.getTime(), DATE_FORMAT[0]));
            Assert.assertTrue(AttributeStandard.isValidHash(a.getTxHash()));
            Assert.assertTrue(AttributeStandard.isValidPoolId(a.getPoolId()));
        }
        return this;
    }
    @Step("get stake list address")
    public StakeKeySteps getStakeListAddress(Map<String, Object> param, String stakeKey){
        sendGet(Endpoints.StakeKeyApi.GET_STAKE_LIST_ADDRESS, param,  Endpoints.StakeKeyApi.STAKE_KEY, stakeKey);
        return this;
    }
    @Step("verify that current page of stake list address")
    public StakeKeySteps then_verifyPageStakeListAddressResponse(StakeListAddressModel stakeListAddressModel, Map<String,Object> param, int defaultSize){
        RequestParams requestParams = new RequestParams(param, 0, defaultSize);
        assertThat(stakeListAddressModel.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        return this;
    }
    @Step("verify that size of stake list address")
    public StakeKeySteps then_verifySizeStakeListAddressResponse(StakeListAddressModel stakeListAddressModel, Map<String,Object> param, int defaultSize){
        RequestParams requestParams = new RequestParams(param, 0, defaultSize);
        assertThat(stakeListAddressModel.getData().size())
                .as("The size of page is wrong")
                .isEqualTo(requestParams.getSize());
        return this;
    }

    @Step("Verify response of top delegator and stake detail")
    public StakeKeySteps then_verifyStakeResponseTopAndDetail(TopDelegators topDelegators, StakeModel stakeModel) {
        assertThat(topDelegators.getData().get(0).getPoolId())
                .as("Value of field 'poolId' is wrong")
                .isEqualTo(stakeModel.getPool().getPoolId());
        return this;
    }
    @Step("Verify response of top delegator and delegator history")
    public StakeKeySteps then_verifyStakeResponseTopAndDelegatorHistory(TopDelegators topDelegators, StakeHistory stakeHistory) {
//        assertThat(topDelegators.getData().get(0).getTickerName())
//                .as("Value of field 'poolId' is wrong")
//                .isEqualTo(stakeHistory.getData().get(0).get);
        return this;
    }
}
