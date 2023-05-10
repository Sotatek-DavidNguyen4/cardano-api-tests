package tests;

import base.BaseTest;
import microservices.pool.models.PoolResponse;
import microservices.pool.steps.PoolSteps;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class PoolTests extends BaseTest {
    private PoolSteps poolSteps = new PoolSteps();
    private PoolResponse poolResponse;
    @Test(description = "API: Get registration pool list", groups = {"pool"})
    public void get_registration_pool_list() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", "");
        params.put("size", "");
        params.put("sort", "");
        poolSteps.when_getRegistrationPoolList(params)
                .validateResponse(HttpURLConnection.HTTP_OK);
        params.put("page", 0);
        params.put("size", 2);
        params.put("sort", "");
        poolSteps.when_getRegistrationPoolList(params)
                .validateResponse(HttpURLConnection.HTTP_OK);
        params.put("page", 1000);
        params.put("size", "");
        params.put("sort", "");
        poolSteps.when_getRegistrationPoolList(params)
                .validateResponse(HttpURLConnection.HTTP_OK);
        params.put("page", "");
        params.put("size", "");
        params.put("sort", "asc");
        poolSteps.when_getRegistrationPoolList(params)
                .validateResponse(HttpURLConnection.HTTP_OK);
        params.put("page", "");
        params.put("size", "");
        params.put("sort", "desc");
        poolSteps.when_getRegistrationPoolList(params)
                .validateResponse(HttpURLConnection.HTTP_OK);
        params.put("page", "#$#$");
        params.put("size", "");
        params.put("sort", "");    }
}
