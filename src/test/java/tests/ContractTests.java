package tests;

import base.BaseTest;
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

public class ContractTests extends BaseTest {
    private ContractSteps contractSteps = new ContractSteps();
    private Contract contract = new Contract();
    private ArrayList<DataContract> dataContracts;

    @Test(description = "verify that get list contract successfully", groups={"contract"},dataProvider = "paramSuccess")
    public void getListParamSuccess(int page, int size, String sort){
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .withSort(sort)
                .getParamsMap();
        contractSteps.getListContracts(param)
                .verifyResponseGetListContract(HttpURLConnection.HTTP_OK);

        contract = contractSteps.saveResponseListContract();

        dataContracts = contract.getDataContracts();
        System.out.println(contract.getCurrentPage());
//        contractSteps.verifyNumberPage(contract.getCurrentPage(), 1)
//                .verifySizeOfResponse(dataContracts.size(), 20);
    }
    @DataProvider(name="paramSuccess")
    public Object[][] dataSetSuccess(){
        return new Object[][]{
                {1,1,"id"}
//                {1,null,null},
//                {null,2,null},
//                {null,null,"id"},
//                {1,1,"id"}
        };
    }
}
