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
import util.SortListUtil;

import java.net.HttpURLConnection;

@Epic("cardano")
@Feature("api-pools")
public class PoolTests extends BaseTest {
    private PoolSteps poolSteps = new PoolSteps();
    private PoolResponse poolResponse;
    private String pathPoolSchema = "schemaJson/pools/pool.json";
    private long totalPages;
    @Test(description = "API: Get registration pool list without sort", groups = {"pool"}, dataProvider = "paramWithPage&Size")
    public void get_registration_pool_list_without_sort(String page, String size) {
        MultiMap params = new MultiValueMap();
        params.put("page", page);
        params.put("size", size);

        PoolResponse poolResponse = (PoolResponse) poolSteps.when_getRegistrationPoolList(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(pathPoolSchema)
                .saveResponseObject(PoolResponse.class);
        poolSteps.then_verifyPoolListResponse(poolResponse, params);
        totalPages = poolResponse.getTotalItems()/20+1;
    }

    @Test(description = "API: Get registration pool list with page = totalPage + 1", groups = {"pool"})
    public void get_registration_pool_list_with_total_page_max() {
        MultiMap params = new MultiValueMap();
        params.put("page", "1");
        params.put("size", "20");

        PoolResponse poolResponse = (PoolResponse) poolSteps.when_getRegistrationPoolList(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(pathPoolSchema)
                .saveResponseObject(PoolResponse.class);
        poolSteps.then_verifyPoolListResponse(poolResponse, params);
        totalPages = poolResponse.getTotalPages()+1;

        params = new MultiValueMap();
        params.put("page", String.valueOf(totalPages));
        poolResponse = (PoolResponse) poolSteps.when_getRegistrationPoolList(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(pathPoolSchema)
                .saveResponseObject(PoolResponse.class);
        poolSteps.then_verifyPoolListResponse(poolResponse, params);
    }
    @Test(description = "API: Get registration pool list with sort", groups = {"pool"}, dataProvider = "paramSort")
    public void get_registration_pool_list_with_sort(String sort) {
        MultiMap params = new MultiValueMap();
        params.put("sort", sort);

        PoolResponse poolResponse = (PoolResponse) poolSteps.when_getRegistrationPoolList(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(pathPoolSchema)
                .saveResponseObject(PoolResponse.class);
        poolSteps.then_verifyPoolListResponse(poolResponse, params);
    }

    @Test(description = "API: Get de-registration pool list without sort", groups = {"pool"}, dataProvider = "paramWithPage&Size")
    public void get_de_registration_pool_list_without_sort(String page, String size) {
        MultiMap params = new MultiValueMap();
        params.put("page", page);
        params.put("size", size);

        PoolResponse poolResponse = (PoolResponse) poolSteps.when_getDeRegistrationPoolList(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(pathPoolSchema)
                .saveResponseObject(PoolResponse.class);
        poolSteps.then_verifyPoolListResponse(poolResponse, params);
    }

    @Test(description = "API: Get de-registration pool list with sort", groups = {"pool"}, dataProvider = "paramSort")
    public void get_de_registration_pool_list_with_sort(String sort) {
        MultiMap params = new MultiValueMap();
        params.put("sort", sort);

        PoolResponse poolResponse = (PoolResponse) poolSteps.when_getDeRegistrationPoolList(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(pathPoolSchema)
                .saveResponseObject(PoolResponse.class);
        poolSteps.then_verifyPoolListResponse(poolResponse, params);
    }

    @Test(description = "API: Get de-registration pool list with page = totalPage + 1", groups = {"pool"})
    public void get_de_registration_pool_list_with_total_page_max() {
        MultiMap params = new MultiValueMap();
        params.put("page", "1");
        params.put("size", "20");

        PoolResponse poolResponse = (PoolResponse) poolSteps.when_getDeRegistrationPoolList(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(pathPoolSchema)
                .saveResponseObject(PoolResponse.class);
        poolSteps.then_verifyPoolListResponse(poolResponse, params);
        totalPages = poolResponse.getTotalPages()+1;

        params = new MultiValueMap();
        params.put("page", String.valueOf(totalPages));
        poolResponse = (PoolResponse) poolSteps.when_getDeRegistrationPoolList(params)
                .validateResponse(HttpURLConnection.HTTP_OK)
                .validateResponseSchema(pathPoolSchema)
                .saveResponseObject(PoolResponse.class);
        poolSteps.then_verifyPoolListResponse(poolResponse, params);
    }

    @DataProvider(name ="paramWithPage&Size")
    public Object[][] dataParamPageAndSize() {
        return new Object[][]{
                // size is null
//                {"6", ""},
                {"abc", ""},
                {"-10", ""},
                {"  ", ""},
                {"@#$!!", ""},
                // page is null
                {"", "1"},
                {"", "abc"},
                {"", "-10"},
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
                {"cost,ASC"},
                {"margin,desc"},
                {"margin,asc"}
        };
    }
}
