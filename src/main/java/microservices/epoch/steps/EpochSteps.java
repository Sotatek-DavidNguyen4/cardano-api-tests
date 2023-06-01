package microservices.epoch.steps;

import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;
import microservices.common.constants.RequestParams;
import microservices.common.steps.BaseSteps;
import microservices.epoch.models.epoch.Epoch;
import microservices.epoch.models.EpochCurrent;
import microservices.epoch.models.epoch.EpochData;
import microservices.epoch.models.epochByEpochNo.EpochByEpochNo;
import microservices.epoch.models.epochByEpochNo.EpochDataByEpochNo;

import microservices.stakeKey.models.deRegistration.StakeDeRegistrationData;
import microservices.stakeKey.steps.StakeKeySteps;
import org.testng.Assert;
import util.SortListUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static constants.DateFormats.DATE_FORMAT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static util.AttributeStandard.*;

public class EpochSteps extends BaseSteps {
    private Epoch epoch = new Epoch();
    private EpochCurrent epochCurrent = new EpochCurrent();
    @Step("Get current epoch")
    public EpochSteps getCurrentEpoch(){
        sendGet(Endpoints.EpochApi.GET_CURRENT_EPOCH);
        return this;
    }
    @Step("Get list epoch")
    public EpochSteps getListEpoch(Map<String,Object> paramsMap){
        sendGet(Endpoints.EpochApi.GET_EPOCH,paramsMap);
        return this;
    }
    @Step("Get list of epoch by no")
        public EpochSteps getEpochByEpochNo(Object epochNo){
        sendGet(Endpoints.EpochApi.GET_EPOCH_BY_EPOCH_NO,Endpoints.EpochApi.EPOCH_NO,epochNo);
        return this;
    }
    @Step("Get list of block epoch by no")
    public EpochSteps getBLockListEpochByEpochNo(int epochNo){
        sendGet(Endpoints.EpochApi.GET_LIST_EPOCH_BY_EPOCH_NO,Endpoints.EpochApi.EPOCH_NO,epochNo);
        return this;
    }
    public EpochSteps getBLockListEpochByEpochNoWithParam(Map<String, Object> param,Integer epochNo){
        sendGet(Endpoints.EpochApi.GET_LIST_EPOCH_BY_EPOCH_NO,param,Endpoints.EpochApi.EPOCH_NO,epochNo);
        return this;
    }
    @Step("Verify current page")
    public EpochSteps verifyCurrentPage(Epoch epoch,Object page){
        if(page instanceof Integer && ((Integer) page).intValue()>0){
            Assert.assertEquals(epoch.getCurrentPage(),page);
        }else {
            Assert.assertEquals(epoch.getCurrentPage(),0);
        }
        return this;
    }
    @Step("Verify response dynamic data of current epoch not null")
    public EpochSteps verifyEpochCurrentResponseNotNull(EpochCurrent epochCurrent){
        Assert.assertNotNull(epochCurrent.getNo());
        Assert.assertNotNull(epochCurrent.getSlot());
        Assert.assertNotNull(epochCurrent.getAccount());
        return this;
    }

    @Step("Verify response static data of current epoch")
    public EpochSteps verifyEpochCurrentResponse(EpochCurrent epochCurrent,Integer totalSlotExpected){
        Assert.assertEquals(epochCurrent.getTotalSlot(),totalSlotExpected);
        return this;
    }
    @Step("Verify value of epoch no")
    public EpochSteps verifyValueEpochNo(EpochData epochData, Integer epochNoExpected){
            Assert.assertEquals(epochData.getNo(),epochNoExpected);
        return this;
    }
    @Step("Verify value of max slot")
    public EpochSteps verifyValueMaxSlot(EpochData epochData, Integer maxSlot){
            Assert.assertEquals(epochData.getMaxSlot(),maxSlot);
        return this;
    }
    @Step("Verify value of epoch no in block")
    public EpochSteps verifyValueEpochNoInBlock(List<EpochDataByEpochNo> epochDataByEpochNos, Integer epochNoExpected){
        for (EpochDataByEpochNo epochData:epochDataByEpochNos) {
            Assert.assertEquals(epochData.getEpochNo(),epochNoExpected);
        }
        return this;
    }
    @Step("Verify response of block epoch not null")
    public EpochSteps verifyResponseEpochNotNull(List<EpochData> epochData){
        for (EpochData epoch:epochData){
            Assert.assertNotNull(epoch.getNo());
            Assert.assertNotNull(epoch.getStatus());
            Assert.assertNotNull(epoch.getBlkCount());
            Assert.assertNotNull(epoch.getOutSum());
            Assert.assertNotNull(epoch.getTxCount());
            Assert.assertNotNull(epoch.getStartTime());
            Assert.assertNotNull(epoch.getEndTime());
            Assert.assertNotNull(epoch.getMaxSlot());
        }
        return this;
    }
    @Step("Verify response of epoch by epochNo in block not null")
    public EpochSteps verifyResponseEpochNoNotNull(EpochData epochData){
            Assert.assertNotNull(epochData.getNo());
            Assert.assertNotNull(epochData.getStatus());
            Assert.assertNotNull(epochData.getBlkCount());
            Assert.assertNotNull(epochData.getOutSum());
            Assert.assertNotNull(epochData.getTxCount());
            Assert.assertNotNull(epochData.getStartTime());
            Assert.assertNotNull(epochData.getEndTime());
        return this;
    }
    @Step("Verify response of epoch by epochNo in block not null")
    public EpochSteps verifyResponseEpochNoInBlockNotNull(List<EpochDataByEpochNo> epochDataByEpochNos){
        for (EpochDataByEpochNo epochData:epochDataByEpochNos) {
            Assert.assertNotNull(epochData.getBlockNo());
            Assert.assertNotNull(epochData.getSlotNo());
            Assert.assertNotNull(epochData.getEpochSlotNo());
            Assert.assertNotNull(epochData.getHash());
            Assert.assertNotNull(epochData.getTime());
            Assert.assertNotNull(epochData.getTxCount());
            Assert.assertNotNull(epochData.getTotalFees());
            Assert.assertNotNull(epochData.getTotalOutput());
            Assert.assertNotNull(epochData.getSlotLeader());

        }
        return this;
    }
    @Step("Verify response of epoch by next epoch")
    public EpochSteps verifyResponseEpochByNextEpoch(EpochByEpochNo epochByEpochNo,boolean isEmpty,Integer totalItemsExpected,Integer totalPagesExpected,Integer currentPageExpected){
        Assert.assertEquals(epochByEpochNo.getData().isEmpty(),isEmpty);
        Assert.assertEquals(epochByEpochNo.getTotalItems(),totalItemsExpected);
        Assert.assertEquals(epochByEpochNo.getTotalPages(),totalPagesExpected);
        Assert.assertEquals(epochByEpochNo.getCurrentPage(),currentPageExpected);
        return this;
    }

    @Step("verify number page of response get list epoch")
    public EpochSteps verifyNumberPage(Integer pageNumberActual, Object pageNumberExpect){
        if((pageNumberExpect instanceof Integer) && (Integer) pageNumberExpect >0){
            Assert.assertEquals(pageNumberActual, pageNumberExpect);
        }else{
            Assert.assertEquals(pageNumberActual.intValue(), 0);
        }
        return this;
    }
    @Step("verify number size of response get list epoch")
    public EpochSteps verifySizeOfResponse(Integer sizeActual, Object sizeExpect){
        if(((sizeExpect instanceof Integer) && (Integer) sizeExpect > 0)){
            Assert.assertEquals(sizeActual, sizeExpect);
        }else {
            Assert.assertEquals(sizeActual.intValue(), 10);
        }
        return this;
    }

    @Step("Verify epoch response")
    public EpochSteps then_verifyEpochResponse(Epoch epoch, Map<String, Object> params) {
        RequestParams requestParams = new RequestParams(params, 0, 10);
        assertThat(epoch.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        assertThat(epoch.getData().size())
                .as("The size of page is wrong")
                .isEqualTo(requestParams.getSize());
        if (requestParams.getSort()!=null) {
            boolean sorted = SortListUtil.isSortedByField(new ArrayList<>(epoch.getData()), requestParams.getSort());
            assertThat(sorted).as("epoch is not sorted by inputted params").isEqualTo(true);
        }
        return this;
    }
    @Step("Verify epoch by no block response")
    public EpochSteps then_verifyEpochByNoBlockResponse(EpochByEpochNo epoch, Map<String, Object> params) {
        RequestParams requestParams = new RequestParams(params, 0, 10);
        assertThat(epoch.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        assertThat(epoch.getData().size())
                .as("The size of page is wrong")
                .isEqualTo(requestParams.getSize());
        if (requestParams.getSort()!=null) {
            boolean sorted = SortListUtil.isSortedByField(new ArrayList<>(epoch.getData()), requestParams.getSort());
            assertThat(sorted).as("epoch is not sorted by inputted params").isEqualTo(true);
        }
        return this;
    }
    @Step("Verify format of Epoch detail by its no")
    public EpochSteps then_verifyFormatOfEpochDetailByNoResponse(EpochData epochData) {
            Assert.assertTrue(isValidDateFormat(epochData.getStartTime(),DATE_FORMAT[0]));
            Assert.assertTrue(isValidDateFormat(epochData.getEndTime(),DATE_FORMAT[0]));
        return this;
    }
    @Step("Verify format of Epoch all list")
    public EpochSteps then_verifyFormatOfEpochAllListResponse(List<EpochData> epochDataList) {
        for(EpochData epochData:epochDataList){
            Assert.assertTrue(isValidDateFormat(epochData.getStartTime(),DATE_FORMAT[0]));
            Assert.assertTrue(isValidDateFormat(epochData.getEndTime(),DATE_FORMAT[0]));
        }
        return this;
    }
    @Step("Verify format of block list of epoch")
    public EpochSteps then_verifyFormatOfBlockListResponse(List<EpochDataByEpochNo> epochDataByEpochNoList) {
        for(EpochDataByEpochNo epochDataByEpochNo:epochDataByEpochNoList){
            Assert.assertTrue(isValidDateFormat(epochDataByEpochNo.getTime(),DATE_FORMAT[0]));
            Assert.assertTrue(isValidHash(epochDataByEpochNo.getHash()));
        }
        return this;
    }
}
