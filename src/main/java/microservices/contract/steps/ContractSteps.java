package microservices.contract.steps;

import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;
import microservices.contract.models.Contract;
import microservices.contract.models.DataContract;
import microservices.token.models.Token;
import microservices.token.steps.TokenSteps;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Map;

public class ContractSteps extends BaseApi {
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
    public ContractSteps verifyNumberPage(int pageNumberActual, Object pageNumberExpect){
        if(pageNumberExpect!=null){
            Assert.assertEquals(pageNumberActual, pageNumberExpect);
        }else{
            Assert.assertEquals(pageNumberActual, 0);
        }
        return this;
    }

    @Step("verify number size of response get list token")
    public ContractSteps verifySizeOfResponse(int sizeActual, Object sizeExpect){
        if(sizeExpect==null ){
            Assert.assertEquals(sizeActual, 20);
        }else {
            Assert.assertEquals(sizeActual, sizeExpect);
        }
        return this;
    }

    @Step("verify address not null")
    public ContractSteps verifyAddressNotNull(ArrayList<DataContract> dataContracts){
        for (DataContract data:dataContracts)
             {
                 Assert.assertNotNull(data.getAddress());
             }
        return this;
    }

    @Step("verify txCount not null")
    public ContractSteps verifyTxCountNotNull(ArrayList<DataContract> dataContracts){
        for (DataContract data:dataContracts)
             {
                 Assert.assertNotNull(data.getTxCount());
             }
        return this;
    }

    @Step("verify balance not null")
    public ContractSteps verifyBalanceNotNull(ArrayList<DataContract> dataContracts){
        for (DataContract data:dataContracts)
             {
                 Assert.assertNotNull(data.getBalance());
             }
        return this;
    }
}
