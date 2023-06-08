package tests.delegation_controller;

import base.BaseTest;
import microservices.delegation.models.HeaderModel;
import microservices.delegation.steps.DelegationTopSteps;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.Map;

public class DelegationPoolTopTests extends BaseTest {
    DelegationTopSteps delegationSteps = new DelegationTopSteps();
    @Test(description = "verify that get data from header successfully", groups={"delegation", "delegation-header"})
    public void verifyGetDataFromPoolDetailAnalyticsSuccessfully(){
        // page = 0 size = 10 search = ""
        int page = 0;
        Map<String, Object> param = new CreateParameters()
                .withPage(page)
                .withSearch("")
                .getParamsMap();
        //successfully
        delegationSteps
                .getDataForDelegationHeader(param)
                .validateStatusCode(HttpURLConnection.HTTP_OK);
    }
}
