package tests.delegation_controller;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.delegation.models.PoolDetailEpochs;
import microservices.delegation.steps.DelegationDetailEpchoSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

@Epic("cardano")
@Feature("api-delegation")
public class DelegationEpochListTests extends BaseTest {
    DelegationDetailEpchoSteps delegationDetailEpchoSteps = new DelegationDetailEpchoSteps();
    String poolView = "pool1pu5jlj4q9w9jlxeu370a3c9myx47md5j5m2str0naunn2q3lkdy";
    @Test(description = "verify that get data from pool detail epochs successfully", groups={"delegation", "delegation-detail-epochs"})
    public void verifyGetDataFromPoolDetailAnalyticsSuccessfully(){
        String poolView = "pool1pu5jlj4q9w9jlxeu370a3c9myx47md5j5m2str0naunn2q3lkdy";
        String jsonSchema = "schemaJson/delegations/pool-detail-epochs.json";
        Object page, size;
        //default
        Map<String, Object> param = new CreateParameters()
                .withPoolView(poolView)
                .getParamsMap();

        PoolDetailEpochs poolDetailEpochs = (PoolDetailEpochs) delegationDetailEpchoSteps
                .getDataForPoolDetailEpochs(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolDetailEpochs.class);
        delegationDetailEpchoSteps.verifyEnoughAmountOfData(poolDetailEpochs.getData().size(), 10);

        //with page = null size = null
        page = null;
        size = null;
        param = new CreateParameters()
                .withPoolView(poolView)
                .withPage(page)
                .getParamsMap();

        poolDetailEpochs = (PoolDetailEpochs) delegationDetailEpchoSteps
                .getDataForPoolDetailEpochs(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(jsonSchema)
                .saveResponseObject(PoolDetailEpochs.class);

        //with poolview = ""
        poolView = "";
        param = new CreateParameters()
                .withPoolView(poolView)
                .withPage(page)
                .getParamsMap();

        poolDetailEpochs = (PoolDetailEpochs) delegationDetailEpchoSteps
                .getDataForPoolDetailEpochs(param)
                .validateStatusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .saveResponseObject(PoolDetailEpochs.class);
    }

    @Test(description = "verify that get data from pool detail epochs successfully with invalid page", groups={"delegation", "delegation-detail-epochs"},dataProvider = "paramInvalidPage")
    public void verifyGetDataFromPoolDetailAnalyticsSuccessfullyWithInvalidPage(Object page){
        //default
        Map<String, Object> param = new CreateParameters()
                .withPoolView(poolView)
                .withPage(page)
                .getParamsMap();

        PoolDetailEpochs poolDetailEpochs = (PoolDetailEpochs) delegationDetailEpchoSteps
                .getDataForPoolDetailEpochs(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolDetailEpochs.class);
        delegationDetailEpchoSteps
                .verifyDataOnRightPage(0, poolDetailEpochs.getCurrentPage())
                .verifyEnoughAmountOfData(poolDetailEpochs.getData().size(), 10);

    }
    @DataProvider(name ="paramInvalidPage")
    public Object[][] dataSetInvalidPage(){
        return new Object[][]{
                {"a"},
                {"-6"},
                {" "},
                {"(jnfj#$%)"},
        };
    }
    @Test(description = "verify that get data from pool detail epochs successfully with invalid size", groups={"delegation", "delegation-detail-epochs"},dataProvider = "paramInvalidSize")
    public void verifyGetDataFromPoolDetailAnalyticsSuccessfullyWithInvalidSize(Object size){
        //default
        Map<String, Object> param = new CreateParameters()
                .withPoolView(poolView)
                .withPageSize(size)
                .getParamsMap();

        PoolDetailEpochs poolDetailEpochs = (PoolDetailEpochs) delegationDetailEpchoSteps
                .getDataForPoolDetailEpochs(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolDetailEpochs.class);
        delegationDetailEpchoSteps
                .verifyDataOnRightPage(0, poolDetailEpochs.getCurrentPage())
                .verifyEnoughAmountOfData(poolDetailEpochs.getData().size(), 10);

    }
    @DataProvider(name ="paramInvalidSize")
    public Object[][] dataSetInvalidSize(){
        return new Object[][]{
                {"a"},
                {"-2"},
                {" "},
                {"(jnfj#$%)"},
        };
    }

}
