package tests.delegation_controller;

import base.BaseTest;
import microservices.delegation.models.HeaderModel;
import microservices.delegation.steps.DelegationHeaderSteps;
import org.testng.annotations.Test;
import sun.net.www.protocol.http.HttpURLConnection;

public class DelegationHeaderTests extends BaseTest {
    DelegationHeaderSteps delegationHeaderSteps = new DelegationHeaderSteps();
    @Test(description = "verify that get data from header successfully", groups={"delegation", "delegation-header"})
    public void verifyGetDataFromPoolDetailAnalyticsSuccessfully(){
        //successfully
        HeaderModel headerModel = (HeaderModel) delegationHeaderSteps
                .getDataForDelegationHeader()
                .validateStatusCode(HttpURLConnection.HTTP_OK)
                .saveResponseObject(HeaderModel.class);
        delegationHeaderSteps.verifyAttributeNotNull(headerModel);
    }
}
