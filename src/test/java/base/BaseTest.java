package base;

import core.BaseApi;
import httprequest.HttpRequest;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        BaseApi.initReqSpec();
        HttpRequest.setBaseUrl("http://172.16.1.230:8033/api/v1/");
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        RestAssured.reset();
    }
}
