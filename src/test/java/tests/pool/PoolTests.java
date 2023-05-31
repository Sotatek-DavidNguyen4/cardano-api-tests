package tests.pool;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import microservices.pool.models.PoolResponse;
import microservices.pool.steps.PoolSteps;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;

@Epic("cardano")
@Feature("api-pools")
public class PoolTests extends BaseTest {
    private PoolSteps poolSteps = new PoolSteps();
    private PoolResponse poolResponse;
    @Test(description = "API: Get registration pool list", groups = {"pool"}, dataProvider = "paramWithPage&Size")
    public void get_registration_pool_list(String page, String size) {
        MultiMap params = new MultiValueMap();
        params.put("page", page);
        params.put("size", size);

        PoolResponse poolResponse = (PoolResponse) poolSteps.when_getRegistrationPoolList(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolResponse.class);
        poolSteps.then_verifyPoolListResponse(poolResponse, params);
    }
    @Test(description = "API: Get registration pool list", groups = {"pool"}, dataProvider = "paramSort")
    public void get_registration_pool_list(String sort) {
        MultiMap params = new MultiValueMap();
        params.put("sort", sort);

        PoolResponse poolResponse = (PoolResponse) poolSteps.when_getRegistrationPoolList(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolResponse.class);
        poolSteps.then_verifyPoolListResponse(poolResponse, params);
    }

    @Test(description = "API: Get de-registration pool list", groups = {"pool"}, dataProvider = "paramWithPage&Size")
    public void get_de_registration_pool_list(String page, String size) {
        MultiMap params = new MultiValueMap();
        params.put("page", page);
        params.put("size", size);

        PoolResponse poolResponse = (PoolResponse) poolSteps.when_getRegistrationPoolList(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolResponse.class);
        poolSteps.then_verifyPoolListResponse(poolResponse, params);
    }

    @Test(description = "API: Get de-registration pool list", groups = {"pool"}, dataProvider = "paramSort")
    public void get_de_registration_pool_list(String sort) {
        MultiMap params = new MultiValueMap();
        params.put("sort", sort);

        PoolResponse poolResponse = (PoolResponse) poolSteps.when_getRegistrationPoolList(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .saveResponseObject(PoolResponse.class);
        poolSteps.then_verifyPoolListResponse(poolResponse, params);
    }

    @DataProvider(name ="paramWithPage&Size")
    public Object[][] dataParamPageAndSize() {
        return new Object[][]{
                // size is null
//                {"6", ""},
                {"a", ""},
                {"-6", ""},
                {"  ", ""},
                {"jnfj#$", ""},
                // page is null
                {"", "1"},
                {"", "a"},
                {"", "-2"},
                {"", "  "},
                {"", "@#$"}
        };
    }

    @DataProvider(name ="paramSort")
    public Object[][] dataParamSort() {
        return new Object[][]{
                {"txTime,desc"},
                {"txTime,ASC"},
                {"pledge,desc"},
                {"pledge,ASC"},
                {"cost,desc"},
                {"cost,desc"},
                {"margin,desc"},
                {"margin,asc"}
        };
    }
}
