package tests.delegation_controller;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.common.models.ErrorResponse;
import microservices.common.steps.BaseSteps;
import microservices.delegation.models.PoolDetailAnalyticsChart;
import microservices.delegation.steps.DelegationPoolDetailAnalyticSteps;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.net.HttpURLConnection;
import util.CreateParameters;

import java.util.Map;


@Epic("cardano")
@Feature("api-delegation")
public class PoolDetailAnalyticTests extends BaseTest {
    DelegationPoolDetailAnalyticSteps delegationPoolDetailAnalyticSteps = new DelegationPoolDetailAnalyticSteps();
    String poolView = "pool1pu5jlj4q9w9jlxeu370a3c9myx47md5j5m2str0naunn2q3lkdy";
    @Test(description = "verify that get data successfully from params 'poolViews' ", groups={"delegation", "delegation-detail-analytics"})
    public void verifyGetDataFromPoolDetailAnalyticsSuccessfully(){
        String jsonSchema = "schemaJson/delegations/pool-detail-analytics.json";
        Map<String, Object> param = new CreateParameters().withPoolView(poolView).getParamsMap();

        //successfully
        PoolDetailAnalyticsChart poolDetailAnalyticsChart = (PoolDetailAnalyticsChart) delegationPoolDetailAnalyticSteps
                .getDataForPoolDetailAnalytics(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(jsonSchema)
                .saveResponseObject(PoolDetailAnalyticsChart.class);

    }
    @Test(description = "verify that get data successfully with invalid poolView ", groups={"delegation", "delegation-detail-analytics"},dataProvider = "paramInvalidPoolView")
    public void verifyGetDataFromPoolDetailAnalyticsSuccessfullyWithInvalidPoolView(Object poolView){
        Map<String, Object> param = new CreateParameters().withPoolView(poolView).getParamsMap();

        //successfully
        PoolDetailAnalyticsChart poolDetailAnalyticsChart = (PoolDetailAnalyticsChart) delegationPoolDetailAnalyticSteps
                .getDataForPoolDetailAnalytics(param)
                .validateStatusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .saveResponseObject(PoolDetailAnalyticsChart.class);

    }
    @DataProvider(name ="paramInvalidPoolView")
    public Object[][] dataSetInvalidSize(){
        return new Object[][]{
                {123},
                {"@#$"},
                {"abc"},
        };
    }
}
