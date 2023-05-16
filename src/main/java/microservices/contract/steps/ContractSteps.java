package microservices.contract.steps;

import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;
import microservices.contract.models.Contract;
import microservices.token.models.Token;
import microservices.token.steps.TokenSteps;
import org.testng.Assert;

import java.util.Map;

public class ContractSteps extends BaseApi {
    private BaseApi baseApi = new BaseApi();
    private Contract contract = new Contract();
    @Step("get list contract")
    public ContractSteps getListContracts(Map<String, Object> paramsContract){
        sendGet(Endpoints.ContractApi.GET_LIST_CONTRACT, paramsContract);
        return this;
    }
    @Step("save response get list contract")
    public Contract saveResponseListContract(){
        contract = (Contract) saveResponseObject(Contract.class);
        return contract;
    }
    @Step("verify number page of response get list contract")
    public ContractSteps verifyNumberPage(int pageNumberActual, int pageNumberExpect){
        if(pageNumberExpect > 0){
            Assert.assertEquals(pageNumberActual, pageNumberExpect);
        }else{
            Assert.assertEquals(pageNumberActual, 0);
        }
        return this;
    }

    @Step("verify number size of response get list token")
    public ContractSteps verifySizeOfResponse(int sizeActual, int sizeExpect){
        if(sizeExpect < 0){
            Assert.assertEquals(sizeActual, 20);
        }else {
            Assert.assertEquals(sizeActual, sizeExpect);
        }
        return this;
    }
    @Step("verify status response of get list token")
    public ContractSteps verifyResponseGetListContract(int statusCode){
        baseApi.validateResponse(statusCode);
        return this;
    }

}
