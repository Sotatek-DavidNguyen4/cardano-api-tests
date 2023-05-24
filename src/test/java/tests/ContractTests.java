package tests;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.contract.models.Contract;
import microservices.contract.models.DataContract;
import microservices.contract.steps.ContractSteps;
import microservices.token.models.DataToken;
import microservices.token.models.Token;
import microservices.token.steps.TokenSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Map;

@Epic("cardano")
@Feature("api-contract")
public class ContractTests extends BaseTest {
    private ContractSteps contractSteps = new ContractSteps();
    private Contract contract = new Contract();
    private ArrayList<DataContract> dataContracts;

    @Test(description = "verify that get list contract successfully", groups={"contract"},dataProvider = "paramSuccess")
    public void getListParamSuccess(Object page, Object size, String sort){
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .withSort(sort)
                .getParamsMap();
        contract = (Contract) contractSteps.getListContracts(param)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(Contract.class);

        dataContracts = contract.getData();
        contractSteps.verifyNumberPage(contract.getCurrentPage(), page)
                     .verifySizeOfResponse(dataContracts.size(), size)
                     .verifyResponseDataNotNull(dataContracts);
    }
    @DataProvider(name="paramSuccess")
    public Object[][] dataSetSuccess(){
        return new Object[][]{
                {2,2,"id"},
                {1,null,null},
                {null,2,null},
                {null,null,"id"},
                {1,1,"id"}
        };
    }

    @Test(description = "verify that get list contract with invalid data", groups={"contract"},dataProvider = "paramUnSuccess")
    public void getListParamUnSuccess(Object page, Object size, String sort){
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .withSort(sort)
                .getParamsMap();
        contractSteps.getListContracts(param)
                .validateResponse(HttpURLConnection.HTTP_OK);
    }
    @DataProvider(name="paramUnSuccess")
    public Object[][] dataSetUnSuccess(){
        return new Object[][]{
                {12222222,null,null},
                {"12222222222222222222",null,null},
                {"@#$",null,null},
        };
    }
}
