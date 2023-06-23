package microservices.contract.steps;

import constants.Endpoints;
import core.BaseApi;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import microservices.common.constants.RequestParams;

import microservices.common.steps.BaseSteps;
import microservices.contract.models.Contract;
import microservices.contract.models.DataContract;
import microservices.contract.models.NativeScriptOfContract;
import org.testng.Assert;
import util.SortListUtil;

import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ContractSteps extends BaseSteps {
    private Contract contract = new Contract();
    @Step("get list contract")
    public ContractSteps getListContracts(Map<String, Object> paramsContract){
        sendGet(Endpoints.ContractApi.GET_LIST_CONTRACT, paramsContract);
        return this;
    }
    @Step("Get native script of contract")
    public ContractSteps getNativeScriptOfContracts(String address){
        sendGet(Endpoints.ContractApi.GET_NATIVE_SCRIPT_OF_CONTRACT,Endpoints.ContractApi.ADDRESS,address);
        return this;
    }

    @Step("verify response data not null")
    public ContractSteps verifyResponseDataNotNull(ArrayList<DataContract> dataContracts){
        for (DataContract data:dataContracts)
             {
                 Assert.assertNotNull(data.getAddress());
                 Assert.assertNotNull(data.getTxCount());
                 Assert.assertNotNull(data.getBalance());
             }
        return this;
    }

    @Step("verify response data of Get native script of contract not null")
    public ContractSteps verifyResponseDataOfNativeNotNull(NativeScriptOfContract nativeScriptOfContract){
        Assert.assertNotNull(nativeScriptOfContract.getType());
        for (int i=0;i<nativeScriptOfContract.getScripts().size();i++)
             {
                 Assert.assertNotNull(nativeScriptOfContract.getScripts().get(i).getType());
                 Assert.assertNotNull(nativeScriptOfContract.getScripts().get(i).getKeyHash());
             }
        return this;
    }

    @Step("Verify contract response")
    public ContractSteps then_verifyContractResponse(Contract contract, Map<String, Object> params) {
        RequestParams requestParams = new RequestParams(params, 0, 20);
        assertThat(contract.getCurrentPage())
                .as("Value of field 'currentPage' is wrong")
                .isEqualTo(requestParams.getPage());
        assertThat(contract.getData().size())
                .as("The size of page is wrong")
                .isEqualTo(requestParams.getSize());
        if (requestParams.getSort()!=null) {
            boolean sorted = SortListUtil.isSortedByField(new ArrayList<>(contract.getData()), requestParams.getSort());
            assertThat(sorted).as("Contract is not sorted by inputted params").isEqualTo(true);
        }
        return this;
    }
}
