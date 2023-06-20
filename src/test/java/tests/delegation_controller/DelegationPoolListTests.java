package tests.delegation_controller;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.delegation.models.HeaderModel;
import microservices.delegation.models.PoolListModel;
import microservices.delegation.steps.DelegationHeaderSteps;
import microservices.delegation.steps.DelegationPoolListSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

@Epic("cardano")
@Feature("api-delegation")
public class DelegationPoolListTests extends BaseTest {
    DelegationPoolListSteps delegationPoolListSteps = new DelegationPoolListSteps();
    String search = "pool1rtseflldkqytw7lm3mpek9g5m04ennkv6ejysxqjj52zyaq46rf";
    @Test(description = "verify that get data from pool list successfully", groups={"delegation", "delegation-pool-list"})
    public void verifyGetDataFromPoolListSuccessfully(){
        // page = 0 size = 10 search = ""
        int page = 0;
        int size = 10;
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .withSearch("")
                .getParamsMap();
        PoolListModel poolListModel = (PoolListModel) delegationPoolListSteps
                .getDataForPoolList(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolListModel.class);
        delegationPoolListSteps
                .verifyAttributeValues(poolListModel)
                .verifyThatDataResponseIsOnCorrectPage(page, poolListModel.getCurrentPage())
                .verifyDataAmountIsCorrect(size, poolListModel.getData().size());

        // page = 0 size = 10 search = "pool1rtseflldkqytw7lm3mpek9g5m04ennkv6ejysxqjj52zyaq46rf"
        param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .withSearch(search)
                .getParamsMap();
        poolListModel = (PoolListModel) delegationPoolListSteps
                .getDataForPoolList(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolListModel.class);
        delegationPoolListSteps
                .verifyAttributeValues(poolListModel)
                .verifyThatDataResponseIsOnCorrectPage(1, poolListModel.getData().size());

        // page = 0 search = "pool1rtseflldkqytw7lm3mpek9g5m04ennkv6ejysxqjj52zyaq46rf"
        param = new CreateParameters()
                .withPage(0)
                .withSearch(search)
                .getParamsMap();
        poolListModel = (PoolListModel) delegationPoolListSteps
                .getDataForPoolList(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolListModel.class);
        delegationPoolListSteps
                .verifyAttributeValues(poolListModel)
                .verifyThatDataResponseIsOnCorrectPage(page, poolListModel.getCurrentPage())
                .verifyThatDataResponseIsOnCorrectPage(1, poolListModel.getData().size());

        // page = 0 search = ""
        param = new CreateParameters()
                .withPage(0)
                .withSearch("")
                .getParamsMap();
        poolListModel = (PoolListModel) delegationPoolListSteps
                .getDataForPoolList(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolListModel.class);
        delegationPoolListSteps
                .verifyAttributeValues(poolListModel)
                .verifyThatDataResponseIsOnCorrectPage(page, poolListModel.getCurrentPage());
        // search = ""
        param = new CreateParameters()
                .withSearch("")
                .getParamsMap();
        poolListModel = (PoolListModel) delegationPoolListSteps
                .getDataForPoolList(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolListModel.class);
        delegationPoolListSteps.verifyAttributeValues(poolListModel);
    }
    @Test(description = "verify that get data from pool list unsuccessfully", groups={"delegation", "delegation-pool-list"}, dataProvider = "paramSort")
    public void verifyGetDataFromPoolListUnsuccessfully(String sort){
        int size = 20;
        Map<String, Object> param = new CreateParameters()
                .withSort(sort)
                .withSearch("")
                .getParamsMap();

        PoolListModel poolListModel = (PoolListModel) delegationPoolListSteps
                .getDataForPoolList(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolListModel.class);
        delegationPoolListSteps.verifyDataAmountIsCorrect(size, poolListModel.getData().size());

    }
    @DataProvider(name ="paramSort")
    public Object[][] dataSetSort(){
        return new Object[][]{
                {"pu.fixedCost,desc"},
                {"pu.fixedCost,asc"},
                {"pu.pledge,desc"},
                {"pu.pledge,asc"},
        };
    }
}
