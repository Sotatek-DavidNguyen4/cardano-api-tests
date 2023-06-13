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
    @Test(description = "verify that get data successfully from params 'poolViews' ", groups={"delegation", "delegation-detail-analytics"}, dataProvider = "paramInvalidPoolView")
    public void verifyGetDataFromPoolDetailAnalyticsSuccessfully(Object poolView){
        Map<String, Object> param = new CreateParameters().withPoolView(poolView).getParamsMap();

        //successfully
        PoolDetailAnalyticsChart poolDetailAnalyticsChart = (PoolDetailAnalyticsChart) delegationPoolDetailAnalyticSteps
                .getDataForPoolDetailAnalytics(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolDetailAnalyticsChart.class);

    }
    @DataProvider(name ="paramInvalidPoolView")
    public Object[][] dataSetInvalidSize(){
        return new Object[][]{
                {"pool1ases3nklh6gyjf74r7dqm89exjfd520z9cefqru959wcccmrdlk"},
//                {123},
//                {"@#$"},
//                {"abc"},

        };
    }
}
