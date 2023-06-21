package tests.delegation_controller;

import base.BaseTest;
import microservices.delegation.models.DelegationTopModel;
import microservices.delegation.models.HeaderModel;
import microservices.delegation.models.PoolDetailInListModel;
import microservices.delegation.steps.DelegationPoolListSteps;
import microservices.delegation.steps.DelegationTopSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

public class DelegationPoolTopTests extends BaseTest {
    DelegationTopSteps delegationSteps = new DelegationTopSteps();
    @Test(description = "verify that get data from header successfully", groups={"delegation", "delegation-top"})
    public void verifyGetDataFromPoolDetailAnalyticsSuccessfully(){
        // page = 0 size = 10 search = ""
        int page, size;
        String jsonSchema = "schemaJson/delegations/delegation-top.json";

        Map<String, Object> param= new CreateParameters()
                .getParamsMap();
        //successfully
        List<PoolDetailInListModel> poolDetailInListModel = (List<PoolDetailInListModel>) delegationSteps
                .getDataForDelegationHeader(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseListObject(PoolDetailInListModel[].class);
        delegationSteps.verifyDataAmountIsCorrect(20, poolDetailInListModel.size());

        size = 1;
        param = new CreateParameters()
                .withPageSize(size)
                .getParamsMap();
        //successfully
        poolDetailInListModel = (List<PoolDetailInListModel>) delegationSteps
                .getDataForDelegationHeader(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseListObject(PoolDetailInListModel[].class);
        delegationSteps.verifyDataAmountIsCorrect(size, poolDetailInListModel.size());

        size = 23;
        param = new CreateParameters()
                .withPageSize(size)
                .getParamsMap();
        //successfully
        poolDetailInListModel = (List<PoolDetailInListModel>) delegationSteps
                .getDataForDelegationHeader(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(jsonSchema)
                .saveResponseListObject(PoolDetailInListModel[].class);
        delegationSteps.verifyDataAmountIsCorrect(20, poolDetailInListModel.size());
    }

    @Test(description = "verify that get data from header successfully with invalid size ", groups={"delegation", "delegation-top"}, dataProvider = "paramInvalidSize")
    public void verifyGetDataFromPoolDetailAnalyticsSuccessfullyWithInvalidSize(Object size){
        Map<String, Object> param= new CreateParameters()
                .withPageSize(size)
                .getParamsMap();
        //successfully
        List<PoolDetailInListModel> poolDetailInListModel = (List<PoolDetailInListModel>) delegationSteps
                .getDataForDelegationHeader(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseListObject(PoolDetailInListModel[].class);
        delegationSteps.verifyDataAmountIsCorrect(20, poolDetailInListModel.size());
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
