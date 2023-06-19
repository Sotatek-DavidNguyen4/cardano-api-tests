package tests.contract;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.contract.models.Contract;
import microservices.contract.models.DataContract;
import microservices.contract.steps.ContractSteps;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.util.ArrayList;

@Epic("cardano")
@Feature("api-contract")
public class TestJsonSchema extends BaseTest {
    private ContractSteps contractSteps = new ContractSteps();
    private Contract contract = new Contract();
    private ArrayList<DataContract> dataContracts;

    @Test(description = "verify that get list contract successfully", groups={"contract"},dataProvider = "paramSuccess")
    public void getListParamSuccess(String page, String size, String sort){
        MultiMap params = new MultiValueMap();
        params.put("page", page);
        params.put("size", size);
        if(!sort.equals("")){
            params.put("sort", sort);
        }

        String schemaPath = "schema/contractSchema/listContractSchema.json";
        contract = (Contract) contractSteps.getListContracts(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(Contract.class);

        dataContracts = contract.getData();
        contractSteps.then_verifyContractResponse(contract,params)
                     .verifyResponseDataNotNull(dataContracts)
                     .verifyJsonSchemaContract(contractSteps.getListContractsResponse(params),schemaPath);
    }
    @DataProvider(name="paramSuccess")
    public Object[][] dataSetSuccess(){
        return new Object[][]{
                {"2","2",""},
                {"1","",""},
                {"","2",""},
                {"","",""},
        };
    }
}
