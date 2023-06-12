package tests.delegation_controller;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.delegation.models.PoolDetailEpochs;
import microservices.delegation.steps.DelegationDetailEpchoSteps;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

@Epic("cardano")
@Feature("api-delegation")
public class DelegationEpochListTests extends BaseTest {
    DelegationDetailEpchoSteps delegationDetailEpchoSteps = new DelegationDetailEpchoSteps();
    String poolView = "pool1ases3nklh6gyjf74r7dqm89exjfd520z9cefqru959wcccmrdlk";
    @Test(description = "verify that get data from pool detail epochs successfully", groups={"delegation", "delegation-detail-epochs"})
    public void verifyGetDataFromPoolDetailAnalyticsSuccessfully(){

        int page = 0;
        int size = 1;
        Map<String, Object> param = new CreateParameters()
                .withPoolView(poolView)
                .getParamsMap();

        delegationDetailEpchoSteps
                .getDataForPoolDetailEpochs(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK);
        //with page = 0
        param = new CreateParameters()
                .withPoolView(poolView)
                .withPage(page)
                .getParamsMap();

        PoolDetailEpochs poolDetailEpochs = (PoolDetailEpochs) delegationDetailEpchoSteps
                .getDataForPoolDetailEpochs(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolDetailEpochs.class);
        delegationDetailEpchoSteps.verifyDataOnRightPage(page, poolDetailEpochs.getCurrentPage());

        //with size = 1
        param = new CreateParameters()
                .withPoolView(poolView)
                .withPageSize(size)
                .getParamsMap();

        poolDetailEpochs = (PoolDetailEpochs) delegationDetailEpchoSteps
                .getDataForPoolDetailEpochs(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolDetailEpochs.class);
        delegationDetailEpchoSteps.verifyEnoughAmountOfData(size, poolDetailEpochs.getData().size());

        //with search page size
        param = new CreateParameters()
                .withPoolView(poolView)
                .withPage(page)
                .withPageSize(size)
                .getParamsMap();

        poolDetailEpochs = (PoolDetailEpochs) delegationDetailEpchoSteps
                .getDataForPoolDetailEpochs(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolDetailEpochs.class);
        delegationDetailEpchoSteps
                .verifyDataOnRightPage(page, poolDetailEpochs.getCurrentPage())
                .verifyEnoughAmountOfData(size, poolDetailEpochs.getData().size());
    }

    @Test(enabled = false, description = "verify that get data from pool detail epochs unsuccessfully", groups={"delegation", "delegation-detail-epochs"})
    public void verifyGetDataFromPoolDetailAnalyticsUnsuccessfully(){
        //wait this will be fixed
        int page = 0;
        int size = 1;
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withPageSize(size)
                .getParamsMap();

        PoolDetailEpochs poolDetailEpochs = (PoolDetailEpochs) delegationDetailEpchoSteps
                .getDataForPoolDetailEpochs(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolDetailEpochs.class);
        delegationDetailEpchoSteps
                .verifyDataValueIsEmpty(poolDetailEpochs.getData());
    }
}
