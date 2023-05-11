package tests;

import base.BaseTest;
import microservices.pool.models.PoolResponse;
import microservices.pool.steps.PoolSteps;
import org.testng.annotations.Test;
import util.CreateParameters;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class PoolTests extends BaseTest {
    private PoolSteps poolSteps = new PoolSteps();
    private PoolResponse poolResponse;
    @Test(description = "API: Get registration pool list", groups = {"pool"})
    public void get_registration_pool_list() {
        Map<String, Object> param = new CreateParameters()
                .withPage("")
                .withPageSize("")
                .withSort("")
                .getParamsMap();
        poolSteps.when_getRegistrationPoolList(param)
                .validateResponse(HttpURLConnection.HTTP_OK);

        param.replace("page", "0");
        poolSteps.when_getRegistrationPoolList(param)
                .validateResponse(HttpURLConnection.HTTP_OK);

        param.replace("size", "1");
        poolSteps.when_getRegistrationPoolList(param)
                .validateResponse(HttpURLConnection.HTTP_OK);

        param.replace("sort", "ascending");
        poolSteps.when_getRegistrationPoolList(param)
                .validateResponse(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @Test(description = "API: Get de-registration pool list", groups = {"pool"})
    public void get_de_registration_pool_list() {
        Map<String, Object> param = new CreateParameters()
                .withPage("")
                .withPageSize("")
                .withSort("")
                .getParamsMap();
        poolSteps.when_getDeRegistrationPoolList(param)
                .validateResponse(HttpURLConnection.HTTP_OK);

        param.replace("page", "0");
        poolSteps.when_getDeRegistrationPoolList(param)
                .validateResponse(HttpURLConnection.HTTP_OK);

        param.replace("size", "1");
        poolSteps.when_getDeRegistrationPoolList(param)
                .validateResponse(HttpURLConnection.HTTP_OK);

        param.replace("sort", "ascending");
        poolSteps.when_getDeRegistrationPoolList(param)
                .validateResponse(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }
}
