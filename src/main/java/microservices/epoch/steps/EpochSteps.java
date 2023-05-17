package microservices.epoch.steps;

import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;
import microservices.contract.models.Contract;
import microservices.contract.models.DataContract;
import microservices.epoch.models.EpochCurrent;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Map;

public class EpochSteps extends BaseApi {
    private EpochCurrent contract = new EpochCurrent();
    @Step("get current epoch")
    public EpochSteps getCurrentEpoch(){
        sendGet(Endpoints.ContractApi.GET_LIST_CONTRACT);
        return this;
    }
    @Step("save response get list contract")
    public Contract saveResponseListContract(){
        contract = (Contract) saveResponseObject(Contract.class);
        return contract;
    }
}
