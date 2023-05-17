package microservices.epoch.steps;

import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;
import microservices.contract.models.Contract;
import microservices.contract.models.DataContract;
import microservices.epoch.models.Epoch;
import microservices.epoch.models.EpochCurrent;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Map;

public class EpochSteps extends BaseApi {
    private Epoch epoch = new Epoch();
    private EpochCurrent epochCurrent = new EpochCurrent();
    @Step("Get current epoch")
    public EpochSteps getCurrentEpoch(){
        sendGet(Endpoints.EpochApi.GET_CURRENT_EPOCH);
        return this;
    }

    @Step("Get list of epoch")
    public EpochSteps getBLockListEpoch(Map<String, Object> paramsEpoch){
        sendGet(Endpoints.EpochApi.GET_LIST_EPOCH_NO,paramsEpoch);
        return this;
    }

    @Step("Get list of epoch by no")
    public EpochSteps getBLockListEpochByNo(int no){
        sendGet(Endpoints.EpochApi.GET_LIST_EPOCH_NO,"no",no);
        return this;
    }
    @Step("Save response get current epoch")
    public EpochCurrent saveResponseCurrentEpoch(){
        epochCurrent = (EpochCurrent) saveResponseObject(EpochCurrent.class);
        return epochCurrent;
    }
    @Step("Save response get list epoch")
    public Epoch saveResponseListEpoch(){
        epoch = (Epoch) saveResponseObject(Epoch.class);
        return epoch;
    }

    @Step("Verify response dynamic data of current epoch not null")
    public EpochSteps verifyEpochCurrentResponseNotNull(EpochCurrent epochCurrent){
        Assert.assertNotNull(epochCurrent.getNo());
        Assert.assertNotNull(epochCurrent.getSlot());
        Assert.assertNotNull(epochCurrent.getAccount());
        return this;
    }

    @Step("Verify response static data of current epoch not null")
    public EpochSteps verifyEpochCurrentResponse(EpochCurrent epochCurrent,int totalSlotExpected){
        Assert.assertEquals(epochCurrent.getTotalSlot(),totalSlotExpected);
        return this;
    }
}
