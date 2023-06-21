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
    Object page, size, search;

    @Test(description = "verify that get data from pool list successfully", groups={"delegation", "delegation-pool-list"})
    public void verifyGetDataFromPoolListSuccessfully(){
        // page = null size = null search = ""
        String pathPoolListSchema = "schemaJson/delegations/delegation-pool-list.json";
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .withSearch(search)
                .getParamsMap();
        delegationPoolListSteps
                .getDataForPoolList(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(pathPoolListSchema);
        // page = null size = null search = "pool1z5uqdk7dzdxaae5633fqfcu2eqzy3a3rgtuvy087fdld7yws0xt"
        page = null;
        size = null;
        search = "pool1z5uqdk7dzdxaae5633fqfcu2eqzy3a3rgtuvy087fdld7yws0xt";
        param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .withSearch(search)
                .getParamsMap();
        PoolListModel poolListModel = (PoolListModel) delegationPoolListSteps
                .getDataForPoolList(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(pathPoolListSchema)
                .saveResponseObject(PoolListModel.class);
        // page = null size = null search = "OctasPool"
        page = null;
        size = null;
        search = "OctasPool";
        param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .withSearch(search)
                .getParamsMap();
        poolListModel = (PoolListModel) delegationPoolListSteps
                .getDataForPoolList(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(pathPoolListSchema)
                .saveResponseObject(PoolListModel.class);

        delegationPoolListSteps
                .verifyDataAmountIsCorrect(1, poolListModel.getData().size());
        // search = "@#$"
        page = null;
        size = null;
        search = "@#$";
        param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .withSearch(search)
                .getParamsMap();
        poolListModel = (PoolListModel) delegationPoolListSteps
                .getDataForPoolList(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(pathPoolListSchema)
                .saveResponseObject(PoolListModel.class);

        delegationPoolListSteps
                .verifyDataAmountIsCorrect(0, poolListModel.getData().size());
    }
    @Test(description = "verify that get data from pool list successfully with page", groups={"delegation", "delegation-pool-list"},dataProvider = "paramInvalidPage")
    public void verifyGetDataFromPoolListsuccessfullyWithPage(Object page){
        search = "";
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withSearch(search)
                .getParamsMap();

        PoolListModel poolListModel = (PoolListModel) delegationPoolListSteps
                .getDataForPoolList(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolListModel.class);
        delegationPoolListSteps.verifyThatDataResponseIsOnCorrectPage(0, poolListModel.getCurrentPage());

    }
    @DataProvider(name ="paramInvalidPage")
    public Object[][] dataSetInvalidPage(){
        return new Object[][]{
                {"a"},
                {"-6"},
                {" "},
                {"jnfj#$%"},
        };
    }
    @Test(description = "verify that get data from pool list successfully with size", groups={"delegation", "delegation-pool-list"},dataProvider = "paramInvalidSize")
    public void verifyGetDataFromPoolListSuccessfullyWithSize(Object size){
        search = "";
    @Test(description = "verify that get data from pool list unsuccessfully", groups={"delegation", "delegation-pool-list"}, dataProvider = "paramSort")
    public void verifyGetDataFromPoolListUnsuccessfully(String sort){
        int size = 20;
        Map<String, Object> param = new CreateParameters()
                .withPageSize(size)
                .withSearch(search)
                .getParamsMap();

        delegationPoolListSteps
                .getDataForPoolList(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK);

    }
    @DataProvider(name ="paramInvalidSize")
    public Object[][] dataSetInvalidSize(){
        return new Object[][]{
                {"a"},
                {"-2"},
                {" "},
                {"jnfj#$%"},
        };
    }
/*    @Test(description = "verify that get data from pool list successfully with sort", groups={"delegation", "delegation-pool-list"},dataProvider = "paramInvalidSort")
    public void verifyGetDataFromPoolListSuccessfullyWithSort(){
        search = "";
        String sort = "pu.fixedCost, desc";
        Map<String, Object> param = new CreateParameters()
                .withSearch(search)
                .withSort(sort)
                .withSort(sort)
                .withSearch("")
                .getParamsMap();

        delegationPoolListSteps
                .getDataForPoolList(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK);
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolListModel.class);
        delegationPoolListSteps.verifyDataAmountIsCorrect(size, poolListModel.getData().size());

    }*/
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
